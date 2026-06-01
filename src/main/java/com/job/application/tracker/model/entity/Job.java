package com.job.application.tracker.model.entity;


import com.job.application.tracker.common.JobType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Column(columnDefinition = "TEXT" , nullable = false)
    @Size(max = 100)
    private String title;

    @NotBlank
    @Column(columnDefinition = "TEXT" , nullable = false)
    @Size(min = 50)
    private String description;

    @Enumerated(EnumType.STRING)
    private JobType type;

    @NotNull
    private Long salary;

    @NotBlank
    @Size(min = 3)
    private String location;

    // relation
    @OneToMany(mappedBy = "job")
    private List<Application> applications;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
