package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserGetDto {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private List<Application> applications;

}
