package com.job.application.tracker.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyCreateDto {
    @NotBlank
    private String name;

}
