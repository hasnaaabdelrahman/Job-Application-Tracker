package com.job.application.tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserUpdateDto {
    @NotBlank
    private String name;
    @NumberFormat
    @Pattern(regexp = "\\d+", message = "Phone number must contain digits only")
    @Size(min = 11, message = "Phone number must be at least 11 digits")
    private String phone;
    @Email
    @NotBlank
    private String email;
    private LocalDate birthDate;
}
