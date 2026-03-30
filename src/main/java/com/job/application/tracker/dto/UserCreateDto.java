package com.job.application.tracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

public class UserCreateDto {
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
}
