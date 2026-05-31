package com.job.application.tracker.service.implementation;

import com.job.application.tracker.exceptions.InvalidTokenException;
import com.job.application.tracker.model.entity.RefreshToken;
import com.job.application.tracker.repository.RefreshTokenRepository;
import com.job.application.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshToken create(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userRepository.findByEmail(username).get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000))
                .build();
        return repository.save(refreshToken);
    }
    public RefreshToken verifyToken(String token) {
        RefreshToken refreshToken = repository.findByToken(token).orElseThrow(
                ()-> new InvalidTokenException("Invalid refresh token")
        );

        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new InvalidTokenException("refresh token was expired login again");
        }
        return refreshToken;
    }
}
