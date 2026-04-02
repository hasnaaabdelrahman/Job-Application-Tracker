package com.job.application.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "users")

public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String name;
    @NumberFormat
    private String phone;
    @Email
    @NotBlank(message = "enter a valid email")
    private String email;
    private LocalDate birthDate;

    // relations
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();
}
