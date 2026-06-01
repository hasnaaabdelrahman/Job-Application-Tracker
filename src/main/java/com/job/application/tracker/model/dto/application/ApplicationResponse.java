package com.job.application.tracker.model.dto.application;

import com.job.application.tracker.common.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private Integer id;
    private ApplicationStatus applicationStatus;
}
