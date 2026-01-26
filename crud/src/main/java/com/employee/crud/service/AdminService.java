package com.employee.crud.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.crud.entity.Employee;
import com.employee.crud.entity.Role;
import com.employee.crud.io.EmployeeRequest;
import com.employee.crud.io.EmployeeUpdateRequest;
import com.employee.crud.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id){
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("employee not found"));
        return employee;
    }

    
    public Employee updateEmployee(Long id, EmployeeUpdateRequest request){
        Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("employee not found"));

        if (request.getDepartment() != null) {
            employee.setDepartment(request.getDepartment());
        }
        if (request.getEmail() != null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getName() != null) {
            employee.setName(request.getName());
        }
        if (request.getSalary() != null) {
            employee.setSalary(request.getSalary());
        }
        if (request.getPhone() != null) {
            employee.setPhone(request.getPhone());
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id){
        if(!employeeRepository.existsById(id)){
            throw new RuntimeException("employee not found");
        }

        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest request){
        Employee employee = new Employee();
        if(employeeRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("employee already exists");
        }

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setPhone(request.getPhone());
        employee.setSalary(request.getSalary());
        employee.setRole(Role.EMPLOYEE);
        employee.setHireDate(LocalDateTime.now());

        return employeeRepository.save(employee);
    }
}
