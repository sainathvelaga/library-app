@Tag(name = "Reviews API")
@RestController
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Check if user reviewed a book")
    @GetMapping("/secure/user/book")
    public boolean reviewBookByUser(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @Operation(summary = "Post a book review")
    @PostMapping("/secure")
    public void postReview(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody ReviewRequest reviewRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        reviewService.postReview(userEmail, reviewRequest);
    }
}
