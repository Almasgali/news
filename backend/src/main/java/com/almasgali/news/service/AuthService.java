package com.almasgali.news.service;

import com.almasgali.news.data.dto.AuthRequest;
import com.almasgali.news.data.dto.RegisterRequest;
import com.almasgali.news.data.model.User;
import com.almasgali.news.exception.auth.UserAlreadyExistsException;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(@Autowired UserRepository userRepository,
                       @Autowired PasswordEncoder passwordEncoder,
                       @Autowired AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

    public User authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return userRepository.findByEmail(request.getEmail())
                .orElseThrow(NoSuchElementException::new);
    }
}
