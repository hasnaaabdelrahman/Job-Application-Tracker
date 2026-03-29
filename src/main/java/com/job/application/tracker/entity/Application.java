package com.job.application.tracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Application {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private enum ApplicationStatus {
        APPLIED,
        INTERVIEW,
        REJECTED,
        ACCEPTED
    }

    //relations
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
}
