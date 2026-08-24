package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.EmailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailDetails, Integer> {
}
