package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationGetDto {
    private Integer id;
    private Application.ApplicationStatus applicationStatus;
}
