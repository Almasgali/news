package com.almasgali.news.service;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.model.User;
import com.almasgali.news.exception.auth.UserAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

public class AuthServiceTests extends CommonTests {

    @Autowired
    AuthService authService;

    @Test
    @Transactional
    public void signupTest() {
        RegisterRequest existedEmail = RegisterRequest.builder()
                .name("a")
                .surname("a")
                .email("a@mail.com")
                .password("aaaaaaaa")
                .build();
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.signup(existedEmail));

        RegisterRequest newEmail = RegisterRequest.builder()
                .name("a")
                .surname("a")
                .email("new@mail.com")
                .password("aaaaaaaa")
                .build();
        User newUser = authService.signup(newEmail);
        Assertions.assertEquals("a", newUser.getName());
        Assertions.assertEquals("a", newUser.getSurname());
        Assertions.assertEquals("new@mail.com", newUser.getUsername());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.signup(newEmail));
    }

    @Test
    @Transactional
    public void authTest() {
        AuthRequest wrongEmail =  AuthRequest.builder().email("wrong@mail.com").password("aaaaaaaa").build();
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.authenticate(wrongEmail));

        AuthRequest wrongPassword =  AuthRequest.builder().email("a@mail.com").password("wrong").build();
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.authenticate(wrongEmail));

        RegisterRequest newEmail = RegisterRequest.builder()
                .name("a")
                .surname("a")
                .email("new@mail.com")
                .password("aaaaaaaa")
                .build();
        User newUser = authService.signup(newEmail);

        AuthRequest okRequest =  AuthRequest.builder().email("new@mail.com").password("aaaaaaaa").build();
        User user = authService.authenticate(okRequest);
        Assertions.assertEquals("a", user.getName());
        Assertions.assertEquals("a", user.getSurname());
        Assertions.assertEquals("new@mail.com", user.getUsername());
    }
}
