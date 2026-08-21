package com.job.application.tracker.repository;


import com.job.application.tracker.model.entity.FileRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRecordRepository extends JpaRepository<FileRecord, Integer> {
}
