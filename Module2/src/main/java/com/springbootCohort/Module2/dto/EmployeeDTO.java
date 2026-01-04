package com.springbootCohort.Module2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbootCohort.Module2.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"active"})
public class EmployeeDTO {
    private Long id;
//    @NotNull(message = "Required Field in Employee: Name")
//    Similarly we can use @NotEmpty
    @NotBlank(message = "Field Name in Employee cannot be blank")
    @Size(min = 3, max = 30, message = "The field Name should be in the range: [3,30]")
    private String name;

    @NotBlank(message = "Email of Employee cannot be blank")
    @Email(message = "Please provide with a valid email")
    private String email;

    @Max(value = 65, message = "Maximum age allowed for an employee is 65")
    @Min(value = 18, message = "Minimum age for an employee should be 18")
    private Integer age;

    @NotBlank(message = "Role of Employee cannot be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can either be USER or ADMIN")
//    We will use our own custom validation
    @EmployeeRoleValidation
    private String role;    // ADMIN, USER

    @Positive(message = "Salary should be a positive value")
    private Integer salary;

    @PastOrPresent(message = "DateOfJoining field in Employee cannot be in future")
    private LocalDate dateOfJoining;
    @JsonProperty("isActive")
    private boolean isActive;
}
