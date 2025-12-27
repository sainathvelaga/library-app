package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.ReviewRequest;
import com.ntd.libraryappbe.service.ReviewService;
import com.ntd.libraryappbe.utils.ExtractJWT;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Reviews API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Check if user reviewed a book")
    @GetMapping("/secure/user/book")
    public boolean reviewBookByUser(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @Operation(summary = "Post a review")
    @PostMapping("/secure")
    public void postReview(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody ReviewRequest reviewRequest) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        reviewService.postReview(userEmail, reviewRequest);
    }
}
