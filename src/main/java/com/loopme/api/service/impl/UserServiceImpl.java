package com.loopme.api.service.impl;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.controller.dto.UserUpdDto;
import com.loopme.api.converter.UserConverter;
import com.loopme.api.exception.UserException;
import com.loopme.api.model.User;
import com.loopme.api.model.UserRole;
import com.loopme.api.repository.UserRepository;
import com.loopme.api.service.AuthenticationService;
import com.loopme.api.service.UserService;
import com.loopme.api.service.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.text.MessageFormat.format;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final UserUtilService userUtilService;
    private final AuthenticationService authenticationService;
    private final UserConverter userConverter;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final PasswordEncoder encoder, final UserUtilService userUtilService,
                           final AuthenticationService authenticationService, final UserConverter userConverter,
                           final UserRepository userRepository) {
        this.encoder = encoder;
        this.userUtilService = userUtilService;
        this.authenticationService = authenticationService;
        this.userConverter = userConverter;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDto createUser(final SignUpRequest signUpRequest) {
        User foundUser = userRepository.findByEmail(signUpRequest.getEmail());
        if (Objects.nonNull(foundUser)) {
            return null;
        }
        checkOperationPermission(signUpRequest.getRole());
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .role(signUpRequest.getRole())
                .apps(Collections.emptySet())
                .build();

        return userConverter.convert(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto update(final Long userId, final UserUpdDto userDto) {
        User foundUser = userRepository.findOne(userId);
        userUtilService.nullCheck(foundUser, userId);
        checkOperationPermission(foundUser.getRole());
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
        checkUserDeleteHimself(userId, authenticationService.getOperationAuthor());
        checkOperationPermission(foundUser.getRole());
        userRepository.delete(userId);

        return Boolean.TRUE;
    }

    @Override
    public UserDto findById(final Long userId) {
        User foundUser = userRepository.findOne(userId);
        if (Objects.isNull(foundUser)) {
            return null;
        }
        return userConverter.convert(foundUser);
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            return Collections.emptyList();
        }

        return userConverter.convert(users);
    }

    private void checkOperationPermission(final UserRole userRole) {
        User operationAuthor = authenticationService.getOperationAuthor();
        if (Objects.equals(UserRole.ROLE_ADMIN, userRole)) {
            throw new UserException(format("user with email: {0} can not perform operation with admin",
                    operationAuthor.getEmail()));
        }
        if (Objects.equals(UserRole.ROLE_ADOPS, operationAuthor.getRole()) &&
                Objects.equals(UserRole.ROLE_ADOPS, userRole)) {
            throw new UserException(format("user with email: {0} can not perform operation with operator",
                    operationAuthor.getEmail()));
        }
    }

    private void checkUserDeleteHimself(Long userId, User operationAuthor) {
        if (Objects.equals(operationAuthor.getId(), userId)) {
            throw new UserException(format("user with email: {0} can not delete himself",
                    operationAuthor.getEmail()));
        }
    }
}
