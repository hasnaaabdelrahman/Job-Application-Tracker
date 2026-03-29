package com.job.application.tracker.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    public enum ApplicationStatus {
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
