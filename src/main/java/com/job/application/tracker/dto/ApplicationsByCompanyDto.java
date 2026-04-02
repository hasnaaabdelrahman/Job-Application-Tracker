package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationsByCompanyDto {
    private Integer id;
    private Application.ApplicationStatus applicationStatus;
    private String companyName;
}
