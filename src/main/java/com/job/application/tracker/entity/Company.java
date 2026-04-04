package com.job.application.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Company {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    @Column(columnDefinition = "TEXT" , nullable = false)
    @Size(min = 3)
    private String name;

    // relation
    @OneToMany(mappedBy = "company")
    private List<Job> jobs = new ArrayList<>();
}
