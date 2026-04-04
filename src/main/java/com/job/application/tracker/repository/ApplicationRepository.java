package com.job.application.tracker.repository;

import com.job.application.tracker.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application , Integer> {
    List<Application> findByApplicationStatus(Application.ApplicationStatus status);
    boolean existsByUserIdAndJobId(Integer userId , Integer jobId);
    List<Application> findByUserId(Integer userId);
    boolean existsById(Integer id);
}
