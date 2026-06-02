package com.job.application.tracker.model.dto.application;

import com.job.application.tracker.common.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationResponse {
    private Integer id;
    private ApplicationStatus applicationStatus;
}
