package com.springbootCohort.Module2.controllers;

import com.springbootCohort.Module2.dto.EmployeeDTO;
import com.springbootCohort.Module2.exceptions.ResourceNotFoundException;
import com.springbootCohort.Module2.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //  Path variable -> mandatory
    @GetMapping(path = "/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDTO> employeeDTO = employeeService.getEmployeeById(employeeId);
//        return employeeDTO
//                    .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
//                    .orElse(ResponseEntity.notFound().build());

        //  For exception handling we can return
//        return employeeDTO
//                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
//                .orElseThrow(() -> new NoSuchElementException());

        //  Or we can use our own custom exception handler
        return employeeDTO
                .map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1))
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));
    }

    //  This exception handler works only under the class EmployeeController
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleElementNotFound(NoSuchElementException exception) {
//        return new ResponseEntity<>("Employee Not Found", HttpStatus.NOT_FOUND);
//    }

    // Request Parameter -> not always mandatory
    @GetMapping(path = "")
    public String getAge(@RequestParam(required = false) Integer age) {
        if(age == null) return "No age is provided";
        else return "Age is " + age;
    }

    //  Lets build some more mappings
    @GetMapping(path = "/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping(path = "/create")
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody @Valid EmployeeDTO inputEmployee) {
        EmployeeDTO createdEmployee = employeeService.createNewEmployee(inputEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping(path = "/{employeeId}")
    public EmployeeDTO updateEmployee(@PathVariable(name = "employeeId") Long id, @RequestBody @Valid EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping(path = "/{employeeId}")
    public boolean deleteEmployeeById(@PathVariable Long employeeId) {
        return employeeService.deleteEmployeeById(employeeId);
    }

    @PatchMapping(path = "/{employeeId}")
    public EmployeeDTO patchEmployee(@PathVariable Long employeeId, @RequestBody Map<String, Object> updates) {
        return employeeService.patchEmployee(employeeId, updates);
    }
}
