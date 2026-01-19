package com.employee.crud.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.employee.crud.entity.Employee;
import com.employee.crud.io.EmployeeRequest;
import com.employee.crud.io.EmployeeResponse;
import com.employee.crud.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public EmployeeResponse registerEmployee(EmployeeRequest request){
        Employee newEmployee = convertToEmployeeEntity(request);
        if (!employeeRepository.existsByEmail(request.getEmail())){
            newEmployee = employeeRepository.save(newEmployee);
            return convertToEmployeeResponse(newEmployee);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "email already registered");
    }

    private Employee convertToEmployeeEntity(EmployeeRequest request) {
        return Employee.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .department(request.getDepartment())
                .phone(request.getPhone())
                .salary(null)
                .hireDate(LocalDateTime.now())
                .resetOtp(null)
                .resetOtpExpireAt(0L)
                .build();

    }

    private EmployeeResponse convertToEmployeeResponse(Employee newEmployee){
        return EmployeeResponse.builder()
                .name(newEmployee.getName())
                .email(newEmployee.getEmail())
                .build();
    }
}
