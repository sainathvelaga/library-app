package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.entity.Book;
import com.ntd.libraryappbe.responsemodels.ShelfCurrentLoansResponse;
import com.ntd.libraryappbe.service.BookService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/books")
@Api(tags = "Books API")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/secure/currentloans")
    @ApiOperation("Get all current loans for the logged-in user")
    public List<ShelfCurrentLoansResponse> currentLoans(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoans(userEmail);
    }

    @GetMapping("/secure/currentloans/count")
    @ApiOperation("Get count of current loans for the logged-in user")
    public int currentLoansCount(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token
    ) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    @ApiOperation("Check if a specific book is already checked out by the logged-in user")
    public Boolean checkoutBookByUser(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam Long bookId
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    @ApiOperation("Renew an existing book loan")
    public void renewLoan(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader(value = "Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam Long bookId
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.renewLoan(userEmail, bookId);
    }
}
