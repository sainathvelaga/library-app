@Tag(name = "Payment API")
@RestController
@RequestMapping("/api/payment/secure")
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
                paymentService.createPaymentIntent(paymentInfoRequest).toJson());
    }

    @Operation(summary = "Complete Stripe payment")
    @PutMapping("/payment-complete")
    public ResponseEntity<String> stripePaymentComplete(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token) throws Exception {

        return paymentService.stripePayment(
                ExtractJWT.payloadJWTExtraction(token, "\"sub\""));
    }
}
