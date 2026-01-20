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
import org.springframework.web.bind.annotation.RestController;

import com.employee.crud.io.AuthRequest;
import com.employee.crud.io.AuthResponse;
import com.employee.crud.service.AppUserDetailService;
import com.employee.crud.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthContoller {
    
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try {
            authenticate(authRequest.getEmail(), authRequest.getPassword());
            final UserDetails userDetails = appUserDetailService.loadUserByUsername(authRequest.getEmail());
            final String jwtToken = jwtUtil.generateToken(userDetails);
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(1))
                .build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new AuthResponse(authRequest.getEmail(), jwtToken));
        } catch(BadCredentialsException ex){
            Map<String, Object> error = new HashMap<>();
            error.put("error", true);
            error.put("message", "email or password incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }  
}

