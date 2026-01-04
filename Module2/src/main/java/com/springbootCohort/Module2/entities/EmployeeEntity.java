package com.springbootCohort.Module2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "employees")
@JsonIgnoreProperties({"active"})
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String role;    // ADMIN | USER
    private Integer salary;
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private boolean isActive;
}
