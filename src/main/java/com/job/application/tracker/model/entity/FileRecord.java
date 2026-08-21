package com.job.application.tracker.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileRecord {
    @Id
    @GeneratedValue
    private Integer id;

    private String fileName;

    private String fileType;

    private long fileSize;

    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;
}
