package com.job.application.tracker.repository;

import com.job.application.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsById(Integer id);
}
