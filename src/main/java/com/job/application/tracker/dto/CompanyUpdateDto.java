package com.job.application.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyUpdateDto {
    @NotBlank
    private String name;

}
