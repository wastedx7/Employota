package com.employee.crud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.crud.entity.Employee;
import com.employee.crud.io.EmployeeRequest;
import com.employee.crud.io.EmployeeUpdateRequest;
import com.employee.crud.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/employees")
    public List<Employee> getEmployees(){
        return adminService.getEmployees();
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(adminService.createEmployee(request));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        adminService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(
                        @PathVariable Long id,
                        @RequestBody EmployeeUpdateRequest request){
        Employee updated = adminService.updateEmployee(id, request);
        return ResponseEntity.ok(updated);
    }
}
