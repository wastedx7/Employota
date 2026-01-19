package com.employee.crud.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "please enter a valid email")
    private String email;
    @NotBlank(message = "set a password")
    @Size(min = 6, message = "password must be atleast 6 characters")
    private String password;
    @Size(min = 8, message = "phone no should be 8 digits")
    private String phone;
    private String department;
}
