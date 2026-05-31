package com.job.application.tracker.controller;

import com.job.application.tracker.Utils.JwtUtils;
import com.job.application.tracker.model.CustomUserDetails;
import com.job.application.tracker.model.dto.token.RefreshTokenRequest;
import com.job.application.tracker.model.dto.token.TokenResponse;
import com.job.application.tracker.model.dto.user.LoginRequest;
import com.job.application.tracker.model.dto.user.UserRequest;
import com.job.application.tracker.model.dto.user.UserResponse;
import com.job.application.tracker.model.entity.RefreshToken;
import com.job.application.tracker.service.implementation.AuthenticationService;
import com.job.application.tracker.service.implementation.RefreshTokenService;
import com.job.application.tracker.service.implementation.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Tag(name = "1- Authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthenticationService service;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "2- Login")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequestDto) {

    return ResponseEntity.ok(service.login(loginRequestDto));
    }

    @Operation(summary = "1- Register")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userDto) {
        return ResponseEntity.ok(userService.add(userDto));
    }

    @Operation(summary = "3- refresh")
    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshToken> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(refreshTokenService.verifyToken(request.getToken()));
    }

    @Operation(summary = "4- reset password")
    @PutMapping("/rest-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal CustomUserDetails current,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        service.changePassword(current.getId() , oldPassword , newPassword);
        return ResponseEntity.ok("password changed successfully!");
    }

    @Operation(summary = "5- logout")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(String token) {
        service.logout(token);
        return ResponseEntity.noContent().build();
    }

}
