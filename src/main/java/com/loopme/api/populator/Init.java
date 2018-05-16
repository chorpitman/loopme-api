package com.loopme.api.populator;

import com.loopme.api.model.App;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Init {
    private static final String ADMIN_NAME = "john";
    private static final String ADMIN_EMAIL = "john@gmail.com";
    private static final UserRole ADMIN_ROLE = UserRole.ROLE_ADMIN;

    private static final String ADOP_NAME = "jack";
    private static final String ADOP_EMAIL = "jack@gmail.com";
    private static final UserRole ADOP_ROLE = UserRole.ROLE_ADOPS;

    private static final String PUBLISHER_NAME = "jim";
    private static final String PUBLISHER_EMAIL = "jim@gmail.com";
    private static final UserRole PUBLISHER_ROLE = UserRole.ROLE_PUBLISHER;

    private static final String PASSWORD = "qwerty78";

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
            User admin = User.builder()
                    .name(ADMIN_NAME)
                    .password(encoder.encode(PASSWORD))
                    .email(ADMIN_EMAIL)
                    .role(ADMIN_ROLE)
                    .apps(getApp(ADOP_NAME))
                    .build();

            //create another user
            User operator = User.builder()
                    .name(ADOP_NAME)
                    .password(encoder.encode(PASSWORD))
                    .email(ADOP_EMAIL)
                    .role(ADOP_ROLE)
                    .apps(getApp(ADOP_NAME))
                    .build();

            //create another user
            User publisher = User.builder()
                    .name(PUBLISHER_NAME)
                    .password(encoder.encode(PASSWORD))
                    .email(PUBLISHER_EMAIL)
                    .role(PUBLISHER_ROLE)
                    .apps(getApp(PUBLISHER_NAME))
                    .build();

            //save
            List<User> users = Arrays.asList(admin, operator, publisher);
            userRepository.save(users);
        }
    }

    private Set<App> getApp(final String name) {
        Set<App> apps = new HashSet<>();
        apps.add(App.builder()
                .name("application " + name)
                .type(AppType.IOS)
                .contentTypes(getContentType())
                .build());
        return apps;
    }

    private Set<ContentType> getContentType() {
        Set<ContentType> contentTypes = new HashSet<>();
        contentTypes.add(ContentType.VIDEO);
        contentTypes.add(ContentType.HTML);
        contentTypes.add(ContentType.IMAGE);

        return getContentType();
    }

}
