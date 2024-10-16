package com.almasgali.news.service;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.AuthResponse;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.dto.RegisterResponse;
import com.almasgali.news.data.model.User;
import com.almasgali.news.exception.auth.UserAlreadyExistsException;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(@Autowired UserRepository userRepository,
                       @Autowired PasswordEncoder passwordEncoder,
                       @Autowired AuthenticationManager authenticationManager,
                       @Autowired JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User signup(RegisterRequest request) {

        String email = request.getEmail();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }

        User user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .isAdmin(false)
                .password(passwordEncoder.encode(request.getPassword())).build();

        return userRepository.save(user);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User authenticatedUser = userRepository.findByEmail(request.getEmail())
                .orElseThrow(NoSuchElementException::new);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        return AuthResponse.builder()
                .id(authenticatedUser.getId())
                .name(authenticatedUser.getName())
                .surname(authenticatedUser.getSurname())
                .message("Пользователь успешно авторизован.")
                .isAdmin(authenticatedUser.isAdmin())
                .token(jwtToken).build();
    }
}
