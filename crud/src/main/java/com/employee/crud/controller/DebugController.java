package com.employee.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DebugController {

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.ok(Map.of(
                "error", "No authentication found"
            ));
        }

        Map<String, Object> info = new HashMap<>();
        info.put("principal", authentication.getPrincipal());
        info.put("authorities", authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
        info.put("authenticated", authentication.isAuthenticated());

        return ResponseEntity.ok(info);
    }
}
