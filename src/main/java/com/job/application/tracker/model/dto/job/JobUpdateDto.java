package com.job.application.tracker.model.dto.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JobUpdateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
}
