package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.entity.Message;
import com.ntd.libraryappbe.requestmodels.AdminQuestionRequest;
import com.ntd.libraryappbe.service.MessageService;
import com.ntd.libraryappbe.utils.ExtractJWT;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages API")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(summary = "Post a message (User)")
    @PostMapping("/secure/add/message")
    public void postMessage(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody Message messageRequest) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messageService.postMessage(messageRequest, userEmail);
    }

    @Operation(summary = "Reply to message (Admin only)")
    @PutMapping("/secure/admin/message")
    public void putMessage(
            @Parameter(description = "JWT token", required = true)
            @RequestHeader("Authorization") String token,
            @RequestBody AdminQuestionRequest adminQuestionRequest) throws Exception {

        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!"admin".equals(role)) {
            throw new Exception("Administration page only.");
        }

        String adminEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messageService.putMessage(adminQuestionRequest, adminEmail);
    }
}
