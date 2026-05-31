package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken , Integer> {
}
