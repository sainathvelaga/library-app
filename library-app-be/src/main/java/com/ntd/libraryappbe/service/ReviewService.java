package com.ntd.libraryappbe.service;

import com.ntd.libraryappbe.entity.Review;
import com.ntd.libraryappbe.repository.ReviewRepository;
import com.ntd.libraryappbe.requestmodels.ReviewRequest;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public boolean userReviewListed(String userEmail, Long bookId) {
        return reviewRepository.findByUserEmailAndBookId(userEmail, bookId).isPresent();
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) {

        Review review = new Review();

        review.setUserEmail(userEmail);
        review.setBookId(reviewRequest.getBookId());
        review.setRating(reviewRequest.getRating());

        String description = reviewRequest.getReviewDescription();
        if (description != null && !description.isEmpty()) {
            review.setReviewDescription(description);
        }

        review.setDate(new Date(System.currentTimeMillis()));

        reviewRepository.save(review);
    }
}