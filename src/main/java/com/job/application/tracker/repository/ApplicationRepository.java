package com.job.application.tracker.repository;

import com.job.application.tracker.entity.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application , Integer> {
    List<Application> findByApplicationStatus(Application.ApplicationStatus status);
    boolean existsByUserIdAndJobId(Integer userId , Integer jobId);
    List<Application> findByUserId(Integer userId);
    boolean existsById(Integer id);
    List<Application> findByJobCompanyIdAndUserId(Integer companyId, Integer userId);
    List<Application> findByApplicationStatusAndUserId(Application.ApplicationStatus status,Integer userId);
    List<Application> findByUser_Id(Integer userId ,Pageable page);
    Optional<Application> findByUser_IdAndId(Integer userId , Integer applicationId);


}
