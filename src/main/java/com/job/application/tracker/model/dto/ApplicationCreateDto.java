package com.job.application.tracker.model.dto;

import com.job.application.tracker.model.entity.Application;
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
