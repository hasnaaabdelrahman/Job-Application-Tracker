package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Company;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobGetDto {
    private Integer id;
    private String title;
    private String description;
}
