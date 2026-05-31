package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Integer> {
    Optional<RefreshToken> findByToken(String token);
}
