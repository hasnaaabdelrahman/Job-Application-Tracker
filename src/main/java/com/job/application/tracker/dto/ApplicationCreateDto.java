package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationCreateDto {
    private Application.ApplicationStatus applicationStatus;
    private Integer user_id;
    private Integer job_id;
}
