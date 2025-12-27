package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.PaymentInfoRequest;
import com.ntd.libraryappbe.service.PaymentService;
import com.ntd.libraryappbe.utils.ExtractJWT;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
@Tag(name = "Payment API")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Create Stripe payment intent")
    @PostMapping("/payment-intent")
    public ResponseEntity<String> createPaymentIntent(
            @RequestBody PaymentInfoRequest paymentInfoRequest) throws Exception {

        return ResponseEntity.ok(
                paymentService.createPaymentIntent(paymentInfoRequest).toJson()
        );
    }

    @Operation(summary = "Complete Stripe payment")
    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return paymentService.stripePayment(userEmail);
    }
}
