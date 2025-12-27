package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.PaymentInfoRequest;
import com.ntd.libraryappbe.service.PaymentService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import com.stripe.model.PaymentIntent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/payment/secure")
@Api(tags = "Payment API")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-intent")
    @ApiOperation("Create a Stripe payment intent")
    public ResponseEntity<String> createPaymentIntent(
            @ApiParam(value = "Payment information", required = true)
            @RequestBody PaymentInfoRequest paymentInfoRequest
    ) throws Exception {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfoRequest);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("/payment-complete")
    @ApiOperation("Complete Stripe payment after successful checkout")
    public ResponseEntity<String> stripePaymentComplete(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email not found");
        }
        return paymentService.stripePayment(userEmail);
    }
}