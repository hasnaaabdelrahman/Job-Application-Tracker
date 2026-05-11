package com.job.application.tracker.model.dto.application;

import com.job.application.tracker.model.entity.Application;
import lombok.Data;

@Data
public class ApplicationUpdateDto {
    private Application.ApplicationStatus applicationStatus;
}
