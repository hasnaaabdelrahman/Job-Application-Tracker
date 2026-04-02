package com.job.application.tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data

public class UserCreateDto {
    @NotBlank
    private String name;
    @NumberFormat
    private String phone;
    @Email
    @NotBlank
    private String email;
    private LocalDate birthDate;
}
