package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import lombok.Data;

@Data
public class ApplicationUpdateDto {
    private Application.ApplicationStatus applicationStatus;
}
