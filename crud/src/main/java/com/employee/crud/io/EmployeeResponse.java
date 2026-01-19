package com.employee.crud.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    
    private String email;
    private String name;
}
