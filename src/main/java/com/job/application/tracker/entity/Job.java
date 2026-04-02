package com.job.application.tracker.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Job {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    // relation
    @OneToMany(mappedBy = "job")
    private List<Application> applications;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
