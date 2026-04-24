package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByCompanyId(Integer companyId);
    List<Job> findByTitleContainingIgnoreCase(String title);
    boolean existsById(Integer id);
}
