package com.job.application.tracker.model.dto.job;

import com.job.application.tracker.common.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JobUpdateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Long salary;

    @NotBlank
    private String location;

    private JobType type;
}
