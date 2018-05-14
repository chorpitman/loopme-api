package com.loopme.api.service.impl;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.controller.dto.UserUpdDto;
import com.loopme.api.converter.UserConverter;
import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.UserRepository;
import com.loopme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public UserDto createUser(final SignUpRequest signUpRequest) {
        User foundUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (Objects.nonNull(foundUser)) {
            return null;
        }
        //valid role
        validRole(signUpRequest.getRole());
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .apps(Collections.emptySet())
                .build();

        //save and convert response dto
        return userConverter.convert(userRepository.save(user));
    }

    @Override
    public UserDto update(final Long userId, final UserUpdDto userDto) {
        User foundUser = userRepository.findOne(userId);
        foundUser.setName(userDto.getName());
        foundUser.setEmail(userDto.getEmail());

        return userConverter.convert(foundUser);
    }

    @Override
    public Boolean delete(final Long userId) {
        User foundUser = userRepository.findOne(userId);
        if (Objects.isNull(foundUser)) {
            return Boolean.FALSE;
        }
        userRepository.delete(userId);
        return Boolean.TRUE;
    }

    @Override
    public String getCurrentAuthUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private void validRole(final UserRole userRole) {
        User currentUser = userRepository.findByEmail(getCurrentAuthUser());
        //админа никто не может создавать
        if (Objects.equals(UserRole.ROLE_ADMIN, userRole)) {
            //todo add custom exception or else
            throw new IllegalArgumentException();
        }
        //если текущий юзер оператор, то создавать оператора он не может
        if (Objects.equals(UserRole.ROLE_ADOPS, currentUser.getRole())) {
            //todo add custom exception or else
            throw new IllegalArgumentException();
        }
    }
}
