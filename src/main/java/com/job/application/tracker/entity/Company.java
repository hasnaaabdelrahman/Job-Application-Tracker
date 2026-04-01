package com.job.application.tracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
    private String name;

    // relation
    @OneToMany(mappedBy = "company")
    private List<Job> jobs = new ArrayList<>();
}
