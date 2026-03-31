package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Job;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyGetDto {
    private Integer id;
    private String name;
}
