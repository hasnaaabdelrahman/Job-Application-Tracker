package com.job.application.tracker.model.dto.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
