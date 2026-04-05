package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDto {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private List<ApplicationDto> application;
    private Set<String> roles = new HashSet<>();
}
