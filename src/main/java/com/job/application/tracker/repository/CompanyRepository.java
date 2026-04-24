package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company , Integer> {
    boolean existsById(Integer id);
}
