package com.job.application.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

@Table(name = "users",
uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "phone")

})
public class User {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    @Column(columnDefinition = "TEXT", nullable = false)
    @Size(min = 3)
    private String name;
    @NumberFormat
    @Size(max = 11)
    @Column(nullable = false)
    @Pattern(regexp = "\\d+", message = "Phone number must contain digits only")
    @Size(min = 11, message = "Phone number must be at least 11 digits")
    private String phone;
    @Email
    @NotBlank(message = "enter a valid email")
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private LocalDate birthDate;

    // relations
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Application> applications = new ArrayList<>();
}
