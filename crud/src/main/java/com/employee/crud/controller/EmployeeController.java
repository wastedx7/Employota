package com.employee.crud.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.crud.io.EmployeeRequest;
import com.employee.crud.io.EmployeeResponse;
import com.employee.crud.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    
    @PostMapping("/register")
    public EmployeeResponse EmployeeResponse(@Valid @RequestBody EmployeeRequest request){
        EmployeeResponse response = employeeService.registerEmployee(request);
        // send email code here
        return response;
    }
}
