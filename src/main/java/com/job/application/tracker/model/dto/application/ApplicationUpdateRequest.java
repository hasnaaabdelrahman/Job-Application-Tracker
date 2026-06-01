package com.job.application.tracker.model.dto.application;

import com.job.application.tracker.common.ApplicationStatus;
import lombok.Data;

@Data
public class ApplicationUpdateRequest {
    private ApplicationStatus applicationStatus;
}
