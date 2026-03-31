package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Job;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CompanyGetDto {
    private Integer id;
    private String name;
    private List<Job> jobs;
}
