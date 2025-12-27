package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.ReviewRequest;
import com.ntd.libraryappbe.service.ReviewService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping(path = "api/reviews")
@Api(tags = "Reviews API")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/secure/user/book")
    @ApiOperation("Check if the logged-in user has already reviewed a book")
    public boolean reviewBookByUser(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam Long bookId
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        return reviewService.userReviewListed(userEmail, bookId);
    }

    @PostMapping("/secure")
    @ApiOperation("Submit a review for a book")
    public void postReview(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token,

            @ApiParam(value = "Review details", required = true)
            @RequestBody ReviewRequest reviewRequest
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        if (userEmail == null) {
            throw new Exception("User email is missing");
        }
        reviewService.postReview(userEmail, reviewRequest);
    }
}