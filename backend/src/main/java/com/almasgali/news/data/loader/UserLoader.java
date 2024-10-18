package com.almasgali.news.data.loader;

import com.almasgali.news.data.model.User;
import com.almasgali.news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("userLoader")
public class UserLoader {

    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserLoader(@Autowired UserRepository userRepository,
                      @Autowired ApplicationEventPublisher publisher,
                      @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void load() {
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(User.builder()
                    .name("Ivan")
                    .surname("Ivanov")
                    .email("ivanov@mail.ru")
                    .password(passwordEncoder.encode("aaaaaaaa"))
                    .isAdmin(false)
                    .build());
            userRepository.save(User.builder()
                    .name("Almas")
                    .surname("Khadyrov")
                    .email("khadyrov@mail.ru")
                    .password(passwordEncoder.encode("bbbbbbbb"))
                    .isAdmin(false)
                    .build());
            userRepository.save(User.builder()
                    .name("Ksenia")
                    .surname("Oskina")
                    .email("oskina@mail.ru")
                    .password(passwordEncoder.encode("cccccccc"))
                    .isAdmin(false)
                    .build());
            userRepository.save(User.builder()
                    .name("Admin")
                    .surname("Adminov")
                    .email("admin@mail.ru")
                    .password(passwordEncoder.encode("password"))
                    .isAdmin(true)
                    .build());
            publisher.publishEvent(new UsersLoadedEvent(this));
        }
    }
}
