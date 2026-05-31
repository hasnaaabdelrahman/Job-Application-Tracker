package com.job.application.tracker.service.implementation;

import com.job.application.tracker.Utils.JwtUtils;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.model.dto.token.TokenResponse;
import com.job.application.tracker.model.dto.user.LoginRequest;
import com.job.application.tracker.model.dto.user.UserRequest;
import com.job.application.tracker.model.dto.user.UserResponse;
import com.job.application.tracker.model.entity.RefreshToken;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.repository.RefreshTokenRepository;
import com.job.application.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;


    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword())
        );
        RefreshToken refreshToken = refreshTokenService.create(request.getEmail());
        return TokenResponse.builder()
                .accessToken(jwtUtils.generateJwtToken(authentication))
                .refreshToken(refreshToken.getToken())
                .build();
    }
    public UserResponse register(UserRequest userDto) {
        return userService.add(userDto);
    }

    public void changePassword(Integer id,String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "user not found with id: " + id
                )
        );
        boolean isMatched = passwordEncoder.matches(oldPassword, user.getPassword());
        if(!isMatched) {
            throw new IllegalArgumentException("password not match the current password");
        }
        if(newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");

        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void logout(String token) {
        RefreshToken refreshToken = refreshTokenService.verifyToken(token);
        tokenRepository.delete(refreshToken);
    }
}
