package com.ilham.controller;

import com.ilham.models.User;
import com.ilham.request.LoginRequest;
import com.ilham.response.AuthResponse;
import com.ilham.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody User user) {
        AuthResponse authResponse = null;
        try {
            authResponse = authService.signUp(user);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new BadCredentialsException(ex.getMessage());
        }
        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = null;
        try {
            authResponse = authService.signIn(loginRequest);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new BadCredentialsException(ex.getMessage());
        }
        return ResponseEntity.ok().body(authResponse);
    }
}
