package com.job.application.tracker.service.implementation;

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
                .expiryDate(Instant.now().minusMillis(600000))
                .build();
        return repository.save(refreshToken);
    }
}
