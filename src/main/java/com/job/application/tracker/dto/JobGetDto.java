package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Company;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class JobGetDto {
    private Integer id;
    private String title;
    private String description;
    private List<Application> applications;
    private Company company;
}
