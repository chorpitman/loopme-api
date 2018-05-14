package com.loopme.api.service.impl;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.converter.UserConverter;
import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.UserRepository;
import com.loopme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(final SignUpRequest signUpRequest) {
        //todo add logger
        User foundUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (Objects.nonNull(foundUser)) {
            throw new IllegalArgumentException();
        }

        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .role(UserRole.ROLE_PUBLISHER)
                .apps(Collections.emptySet())
                .build();

        //save and convert response dto
        return userConverter.convert(userRepository.save(user));
    }

    @Override
    public UserDto update(UserDto userDto) {
        return null;
    }

    @Override
    public String getCurrentAuthUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}