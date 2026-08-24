package com.job.application.tracker.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class EmailDetails {
    @Id
    @GeneratedValue
    private Integer id;
    private String subject;
    private String recipient;
    private String body;

    @ManyToOne
    private User user;
}
