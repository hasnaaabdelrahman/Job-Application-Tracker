package com.job.application.tracker.entity;


import jakarta.persistence.*;
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
    private String title;
    private String description;

    // relation
    @OneToMany(mappedBy = "Job")
    private List<Application> applications;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
