package com.job.application.tracker.model.dto.application;

import com.job.application.tracker.common.ApplicationStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCreateRequest {
    private ApplicationStatus applicationStatus;
    private Integer user_id;
    private Integer job_id;
}
