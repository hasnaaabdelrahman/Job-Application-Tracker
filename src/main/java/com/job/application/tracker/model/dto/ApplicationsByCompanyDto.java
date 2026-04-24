package com.job.application.tracker.model.dto;

import com.job.application.tracker.model.entity.Application;
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
