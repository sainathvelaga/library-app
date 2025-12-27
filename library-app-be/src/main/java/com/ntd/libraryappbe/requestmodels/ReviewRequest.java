package com.ntd.libraryappbe.requestmodels;

import lombok.Data;

@Data
public class ReviewRequest {

    private Long bookId;
    private int rating;
    private String reviewDescription;
}