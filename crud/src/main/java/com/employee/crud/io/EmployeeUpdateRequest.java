package com.employee.crud.io;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {
    
    private String name;
    private String email;
    private String phone;
    private String department;
    private String salary;
}
    

