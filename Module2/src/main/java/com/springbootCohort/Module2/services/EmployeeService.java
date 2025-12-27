package com.springbootCohort.Module2.services;

import com.springbootCohort.Module2.dto.EmployeeDTO;
import com.springbootCohort.Module2.entities.EmployeeEntity;
import com.springbootCohort.Module2.repositories.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepo employeeRepo, ModelMapper modelMapper) {
        this.employeeRepo = employeeRepo;
        this.modelMapper = modelMapper;
    }



    public EmployeeDTO getEmployeeById(Long id) {
        EmployeeEntity employeeEntity = employeeRepo.findById(id).orElse(null);
        return modelMapper.map(employeeEntity, EmployeeDTO.class);
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
        if(exists) return false;
        return true;
    }
}
