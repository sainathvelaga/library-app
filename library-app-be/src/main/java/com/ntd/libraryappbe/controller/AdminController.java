package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.AddBookRequest;
import com.ntd.libraryappbe.service.AdminService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin API")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Increase book quantity (Admin only)")
    @PutMapping("/secure/increase/book/quantity")
    public void increaseBookQuantity(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) throw new Exception("Administration page only!");
        adminService.increaseBookQuantity(bookId);
    }

    @Operation(summary = "Decrease book quantity (Admin only)")
    @PutMapping("/secure/decrease/book/quantity")
    public void decreaseBookQuantity(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) throw new Exception("Administration page only!");
        adminService.decreaseBookQuantity(bookId);
    }

    @Operation(summary = "Add a new book (Admin only)")
    @PostMapping("/secure/add/book")
    public void postBook(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody AddBookRequest addBookRequest) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) throw new Exception("Administration page only!");
        adminService.postBook(addBookRequest);
    }

    @Operation(summary = "Delete a book (Admin only)")
    @DeleteMapping("/secure/delete/book")
    public void deleteBook(
            @Parameter(description = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,
            @Parameter(description = "Book ID", required = true)
            @RequestParam Long bookId) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) throw new Exception("Administration page only!");
        adminService.deleteBook(bookId);
    }
}
