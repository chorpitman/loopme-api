package com.loopme.api.populator;

import com.loopme.api.model.App;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.AppRepository;
import com.loopme.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    private final AppRepository appRepository;

    @Autowired
    public Init(final UserRepository userRepository, final PasswordEncoder encoder, final AppRepository appRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.appRepository = appRepository;
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
                    .build();

            //create another user
            User operator = User.builder()
                    .name(ADOP_NAME)
                    .password(encoder.encode(PASSWORD))
                    .email(ADOP_EMAIL)
                    .role(ADOP_ROLE)
                    .build();

            //create another user
            User publisher = User.builder()
                    .name(PUBLISHER_NAME)
                    .password(encoder.encode(PASSWORD))
                    .email(PUBLISHER_EMAIL)
                    .role(PUBLISHER_ROLE)
                    .build();
            //save
            List<User> users = Arrays.asList(admin, operator, publisher);
            userRepository.save(users);
            saveUserInApp(Arrays.asList(operator, publisher));
        }
    }

    private void saveUserInApp(final List<User> users) {
        List<App> apps = new ArrayList<>();
        for (User user : users) {
            apps.add(App.builder()
                    .name("application " + user.getName())
                    .type(AppType.IOS)
                    .contentTypes(getContentType())
                    .user(user)
                    .build());
        }
        appRepository.save(apps);
    }

    private Set<ContentType> getContentType() {
        Set<ContentType> contentTypes = new HashSet<>(Arrays.asList(ContentType.values()));

        return contentTypes;
    }
}
