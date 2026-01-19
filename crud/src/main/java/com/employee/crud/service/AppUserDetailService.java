package com.employee.crud.service;


import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.crud.entity.Admin;
import com.employee.crud.entity.Employee;
import com.employee.crud.repository.AdminRepository;
import com.employee.crud.repository.EmployeeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // 1. Try Admin first
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return buildUserDetails(
                admin.getEmail(),
                admin.getPassword(),
                admin.getRole().name()
            );
        }

        // 2. Try Employee
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            return buildUserDetails(
                employee.getEmail(),
                employee.getPassword(),
                employee.getRole().name()
            );
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails buildUserDetails(String email, String password, String roleName) {

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(roleName));

        return new User(
            email,
            password,
            authorities
        );
    }
}
