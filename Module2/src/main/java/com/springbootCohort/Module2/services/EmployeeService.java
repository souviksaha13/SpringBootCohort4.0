package com.springbootCohort.Module2.services;

import com.springbootCohort.Module2.dto.EmployeeDTO;
import com.springbootCohort.Module2.entities.EmployeeEntity;
import com.springbootCohort.Module2.repositories.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
    }



    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<EmployeeEntity> employeeEntity = employeeRepo.findById(id);
        return employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> allEmployeeEntities = employeeRepo.findAll();
        return allEmployeeEntities
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        EmployeeEntity newEmployee = employeeRepo.save(employeeEntity);
        return modelMapper.map(newEmployee, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        EmployeeEntity existingEmployee = employeeRepo.findById(id).orElse(null);

        if(existingEmployee == null) {
            //  we can change the logic as per the business usecase
            EmployeeEntity newEmployee = modelMapper.map(employeeDTO, EmployeeEntity.class);
            newEmployee.setId(id);
            newEmployee = employeeRepo.save(newEmployee);
            return modelMapper.map(newEmployee, EmployeeDTO.class);
        }

        EmployeeEntity updatedEmployee = modelMapper.map(employeeDTO, EmployeeEntity.class);
        updatedEmployee.setId(id);
        EmployeeEntity savedEmployee = employeeRepo.save(updatedEmployee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    public boolean deleteEmployeeById(Long id) {
        boolean exists = employeeRepo.existsById(id);
        if(!exists) return false;
        employeeRepo.deleteById(id);
        exists = employeeRepo.existsById(id);
        return !exists;
    }

    public EmployeeDTO patchEmployee(Long employeeId, Map<String, Object> updates) {
        boolean exists = employeeRepo.existsById(employeeId);
        if(!exists) throw new IllegalArgumentException("No such employee exists to be updated");

        EmployeeEntity existingEmployee = employeeRepo.findById(employeeId).orElse(null);
        if(existingEmployee == null) return null;
        System.out.println("Email before changing: " + existingEmployee.getEmail());
        updates.forEach((field, value) -> {
            //  we can manually change the fields but let's use Reflection (for learning purpose)
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(EmployeeEntity.class, field);
            fieldToBeUpdated.setAccessible(true);

            // Special handling for LocalDate
            if (fieldToBeUpdated.getType().equals(LocalDate.class)) {
                value = LocalDate.parse(value.toString());
            }

            ReflectionUtils.setField(fieldToBeUpdated, existingEmployee, value);
        });

        System.out.println("Email after changing: " + existingEmployee.getEmail());

        return modelMapper.map(employeeRepo.save(existingEmployee), EmployeeDTO.class);
    }
}
