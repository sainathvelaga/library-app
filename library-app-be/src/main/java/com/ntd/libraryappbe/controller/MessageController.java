package com.ntd.libraryappbe.controller;

import com.ntd.libraryappbe.entity.Message;
import com.ntd.libraryappbe.requestmodels.AdminQuestionRequest;
import com.ntd.libraryappbe.service.MessageService;
import com.ntd.libraryappbe.utils.ExtractJWT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RestController
@RequestMapping("api/messages")
@Api(tags = "Messages API")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/secure/add/message")
    @ApiOperation("Post a new message or question (User)")
    public void postMessage(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Message payload", required = true)
            @RequestBody Message messageRequest
    ) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messageService.postMessage(messageRequest, userEmail);
    }

    @PutMapping("/secure/admin/message")
    @ApiOperation("Reply to a user message (Admin only)")
    public void putMessage(
            @ApiParam(value = "JWT Authorization token", required = true)
            @RequestHeader("Authorization") String token,

            @ApiParam(value = "Admin response payload", required = true)
            @RequestBody AdminQuestionRequest adminQuestionRequest
    ) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        String role = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");

        if (role == null || !role.equals("admin")) {
            throw new Exception("Administration page only.");
        }

        messageService.putMessage(adminQuestionRequest, userEmail);
    }
}
