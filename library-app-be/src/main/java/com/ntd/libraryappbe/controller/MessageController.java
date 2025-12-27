@Tag(name = "Messages API")
@RestController
@RequestMapping("api/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Post a message (User)")
    @PostMapping("/secure/add/message")
    public void postMessage(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody Message messageRequest) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messageService.postMessage(messageRequest, userEmail);
    }

    @Operation(summary = "Reply to message (Admin only)")
    @PutMapping("/secure/admin/message")
    public void putMessage(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) throw new Exception("Administration page only.");
        messageService.putMessage(adminQuestionRequest,
                ExtractJWT.payloadJWTExtraction(token, "\"sub\""));
    }
}
