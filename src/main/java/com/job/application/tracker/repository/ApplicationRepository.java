package com.job.application.tracker.repository;

import com.job.application.tracker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application , Integer> {
}
