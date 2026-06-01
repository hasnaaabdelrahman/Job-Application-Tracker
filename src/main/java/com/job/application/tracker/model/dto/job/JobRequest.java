package com.job.application.tracker.model.dto.job;

import com.job.application.tracker.common.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull
    private Long salary;

    private JobType type;

    private Integer companyId;
}
