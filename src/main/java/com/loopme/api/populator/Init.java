package com.loopme.api.populator;

import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class Init {
    private static final String USER_NAME = "oleg";
    private static final String USER_PASSWORD = "qwerty78";
    private static final String USER_EMAIL = "snake@gmail.com";
    private static final UserRole USER_ROLE = UserRole.ROLE_ADMIN;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public Init(final UserRepository userRepository, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        List<User> all = userRepository.findAll();
        if (all.size() == 0) {
            User user = User.builder()
                    .name(USER_NAME)
                    .password(encoder.encode(USER_PASSWORD))
                    .email(USER_EMAIL)
                    .role(USER_ROLE)
                    .apps(Collections.emptySet())
                    .build();

            userRepository.save(user);
        }
    }
}
