package com.almasgali.news.controller;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.AuthResponse;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.dto.RegisterResponse;
import com.almasgali.news.service.AuthService;
import com.almasgali.news.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;

    public AuthController(@Autowired JwtService jwtService, @Autowired AuthService authService) {
        this.jwtService = jwtService;
        this.authenticationService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerUserDto) {
        RegisterResponse registerResponse = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest loginUserDto) {
        AuthResponse loginResponse = authenticationService.authenticate(loginUserDto);
        return ResponseEntity.ok(loginResponse);
    }
}
