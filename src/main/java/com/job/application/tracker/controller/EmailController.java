package com.job.application.tracker.controller;

import com.job.application.tracker.model.CustomUserDetails;
import com.job.application.tracker.model.dto.email.EmailRequest;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.service.implementation.EmailService;
import com.job.application.tracker.service.implementation.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email")
public class EmailController {
    public final EmailService service;
    public final UserService userService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@AuthenticationPrincipal CustomUserDetails current,@Valid @RequestBody EmailRequest request) {
        User user = userService.findByUsername(current.getUsername());
        service.sendSimpleMessage(user.getEmail(), request.userId, request.subject, request.text);
        return ResponseEntity.accepted().build();
    }
}
