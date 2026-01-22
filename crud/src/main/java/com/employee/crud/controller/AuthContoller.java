package com.employee.crud.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.crud.io.AuthRequest;
import com.employee.crud.io.AuthResponse;
import com.employee.crud.service.AppUserDetailService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthContoller {
    
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpSession session) {
        try {
            authenticate(authRequest.getEmail(), authRequest.getPassword());
            
            final UserDetails userDetails = appUserDetailService.loadUserByUsername(authRequest.getEmail());
            
            // Store user information in session
            session.setAttribute("user", authRequest.getEmail());
            session.setAttribute("authenticated", true);
            
            Map<String, Object> response = new HashMap<>();
            response.put("email", authRequest.getEmail());
            response.put("message", "Login successful");
            
            return ResponseEntity.ok().body(response);
            
        } catch(BadCredentialsException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "email or password incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().body(Map.of("message", "Logged out successfully"));
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }  
}

