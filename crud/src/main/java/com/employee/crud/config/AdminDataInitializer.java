package com.employee.crud.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.employee.crud.entity.Admin;
import com.employee.crud.entity.Role;
import com.employee.crud.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String adminEmail = "admin@system.com";

        if (adminRepository.existsByEmail(adminEmail)) {
            return; // prevents duplicate admin creation
        }

        Admin admin = Admin.builder()
                .email(adminEmail)
                .password(passwordEncoder.encode("Admin@123"))
                .role(Role.ROLE_ADMIN)
                .build();

        adminRepository.save(admin);

        System.out.println("Default admin created: " + adminEmail);
    }
}
