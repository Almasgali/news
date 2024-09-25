package com.almasgali.news.controller;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.AuthResponse;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.dto.RegisterResponse;
import com.almasgali.news.data.model.User;
import com.almasgali.news.service.AuthService;
import com.almasgali.news.service.JwtService;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authenticationService;

    public AuthController(@Autowired JwtService jwtService, @Autowired AuthService authService) {
        this.jwtService = jwtService;
        this.authenticationService = authService;
        Assert.notNull(this.jwtService);
        Assert.notNull(this.authenticationService);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerUserDto) {
        authenticationService.signup(registerUserDto);
        RegisterResponse registerResponse = RegisterResponse.builder()
                .message("Пользователь успешно зарегистрирован.").build();
        return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        AuthResponse loginResponse = AuthResponse.builder()
                .id(authenticatedUser.getId())
                .name(authenticatedUser.getName())
                .surname(authenticatedUser.getSurname())
                .message("Пользователь успешно авторизован.")
                .token(jwtToken).build();

        return ResponseEntity.ok(loginResponse);
    }
}
