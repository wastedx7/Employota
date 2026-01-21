package com.employee.crud.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.crud.entity.Employee;
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

    @Transactional
    public void updateEmployee(EmployeeUpdateRequest request){
        Employee employee = employeeRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("employee not found"));

        employee.setDepartment(request.getDepartment());
        employee.setEmail(request.getEmail());
        employee.setName(request.getName());
        employee.setSalary(request.getSalary());
        employee.setPhone(request.getPhone());

        employeeRepository.save(employee);
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
        employee.setHireDate(LocalDateTime.now());

        return employeeRepository.save(employee);
    }
}
