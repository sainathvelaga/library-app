package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.requestmodels.AddBookRequest;
import com.ntd.libraryappbe.service.AdminService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/admin")
@Api(tags = "Admin API")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PutMapping("/secure/increase/book/quantity")
    @ApiOperation("Increase quantity of a book (Admin only)")
    public void increaseBookQuantity(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam("bookId") Long bookId
    ) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only!");
        }
        adminService.increaseBookQuantity(bookId);
    }

    @PutMapping("/secure/decrease/book/quantity")
    @ApiOperation("Decrease quantity of a book (Admin only)")
    public void decreaseBookQuantity(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam("bookId") Long bookId
    ) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only!");
        }
        adminService.decreaseBookQuantity(bookId);
    }

    @PostMapping("/secure/add/book")
    @ApiOperation("Add a new book to the library (Admin only)")
    public void postBook(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Book details", required = true)
            @RequestBody AddBookRequest addBookRequest
    ) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only!");
        }
        adminService.postBook(addBookRequest);
    }

    @DeleteMapping("/secure/delete/book")
    @ApiOperation("Delete a book from the library (Admin only)")
    public void deleteBook(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Book ID", required = true)
            @RequestParam("bookId") Long bookId
    ) throws Exception {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only!");
        }
        adminService.deleteBook(bookId);
    }
}
