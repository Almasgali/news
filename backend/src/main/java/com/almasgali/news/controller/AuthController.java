package com.almasgali.news.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.AuthResponse;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.model.User;
import com.almasgali.news.service.AuthService;
import com.almasgali.news.service.JwtService;

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
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid RegisterRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthResponse loginResponse = AuthResponse.builder().token(jwtToken).build();

        return ResponseEntity.ok(loginResponse);
    }
}
