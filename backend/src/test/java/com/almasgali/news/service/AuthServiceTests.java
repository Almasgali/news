package com.almasgali.news.service;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.AuthResponse;
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
        RegisterRequest existedEmail = new RegisterRequest();
        existedEmail.setName("a");
        existedEmail.setSurname("a");
        existedEmail.setEmail("ivanov@mail.ru");
        existedEmail.setPassword("aaaaaaaa");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.signup(existedEmail));

        RegisterRequest newEmail = new RegisterRequest();
        newEmail.setName("a");
        newEmail.setSurname("a");
        newEmail.setEmail("new@mail.com");
        newEmail.setPassword("aaaaaaaa");
        User newUser = authService.signup(newEmail);
        Assertions.assertEquals("a", newUser.getName());
        Assertions.assertEquals("a", newUser.getSurname());
        Assertions.assertEquals("new@mail.com", newUser.getUsername());
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> authService.signup(newEmail));
    }

    @Test
    @Transactional
    public void authTest() {
        AuthRequest wrongEmail = new AuthRequest();
        wrongEmail.setEmail("wrong@mail.com");
        wrongEmail.setPassword("aaaaaaaa");
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.authenticate(wrongEmail));

        AuthRequest wrongPassword = new AuthRequest();
        wrongPassword.setEmail("a@mail.com");
        wrongPassword.setPassword("wrong");
        Assertions.assertThrows(BadCredentialsException.class, () -> authService.authenticate(wrongEmail));
        RegisterRequest newEmail = new RegisterRequest();
        newEmail.setName("newName");
        newEmail.setSurname("newSurname");
        newEmail.setEmail("new@mail.com");
        newEmail.setPassword("aaaaaaaa");

        authService.signup(newEmail);

        AuthRequest okRequest = new AuthRequest();
        okRequest.setEmail("new@mail.com");
        okRequest.setPassword("aaaaaaaa");
        AuthResponse authResponse = authService.authenticate(okRequest);
        Assertions.assertEquals("newName", authResponse.getName());
        Assertions.assertEquals("newSurname", authResponse.getSurname());
    }
}
