package com.job.application.tracker.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

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

    // relation
    @OneToMany(mappedBy = "job")
    private List<Application> applications;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

}
