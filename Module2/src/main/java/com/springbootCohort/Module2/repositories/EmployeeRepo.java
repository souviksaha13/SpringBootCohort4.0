package com.springbootCohort.Module2.repositories;

import com.springbootCohort.Module2.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {

}
