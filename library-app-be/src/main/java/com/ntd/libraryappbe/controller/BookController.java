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
@Tag(name = "Books API")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get current loans of logged-in user")
    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoans(userEmail);
    }

    @Operation(summary = "Get current loans count")
    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @Operation(summary = "Check if a book is checked out by user")
    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBookByUser(userEmail, bookId);
    }

    @Operation(summary = "Checkout a book")
    @PutMapping("/secure/checkout")
    public Book checkoutBook(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }

    @Operation(summary = "Return a book")
    @PutMapping("/secure/return")
    public void returnBook(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.returnBook(userEmail, bookId);
    }

    @Operation(summary = "Renew a book loan")
    @PutMapping("/secure/renew/loan")
    public void renewLoan(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.renewLoan(userEmail, bookId);
    }
}
