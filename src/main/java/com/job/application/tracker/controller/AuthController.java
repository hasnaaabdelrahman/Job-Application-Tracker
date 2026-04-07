package com.job.application.tracker.controller;

import com.job.application.tracker.config.JwtUtils;
import com.job.application.tracker.dto.LoginRequestDto;
import com.job.application.tracker.dto.UserCreateDto;
import com.job.application.tracker.dto.UserGetDto;
import com.job.application.tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Tag(name = "1- Authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Operation(summary = "2- Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        String token = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(token);
    }
    @Operation(summary = "1- Register")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDto userDto) {
        UserGetDto user = userService.add(userDto);
        return ResponseEntity.ok(user);
    }
}
