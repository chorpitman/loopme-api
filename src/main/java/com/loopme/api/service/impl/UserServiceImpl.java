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
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserUtilService userUtilService;
    @Autowired
    private AuthenticationService authenticationService;
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
        validRole(signUpRequest.getRole());
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
        checkOperationPermission(foundUser);
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
        checkOperationPermission(foundUser);
        userRepository.delete(userId);

        return Boolean.TRUE;
    }

    @Override
    public UserDto findById(final Long userId) {
        //todo tnink about lazy
        User foundUser = userRepository.findOne(userId);
        if (Objects.isNull(foundUser)) {
            return null;
        }
        return userConverter.convert(foundUser);
    }

    @Override
    public List<UserDto> findAll() {
        //todo tnink about lazy
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            return Collections.emptyList();
        }

        return userConverter.convert(users);
    }

    private void validRole(final UserRole userRole) {
        User operationAuthor = authenticationService.getOperationAuthor();
        //no one can create admin
        if (Objects.equals(UserRole.ROLE_ADMIN, userRole)) {
            throw new UserException(format("user with email: {0} can not perform operation with admin", operationAuthor.getEmail()));
        }

        //operator can not create operator
        if (Objects.equals(UserRole.ROLE_ADOPS, operationAuthor.getRole())) {
            throw new UserException(format("user with email: {0} can not perform operation with operator", operationAuthor.getEmail()));
        }
    }

    private void checkOperationPermission(final User foundUser) {
        User operationAuthor = authenticationService.getOperationAuthor();
        //no one can update admin
        if (Objects.equals(UserRole.ROLE_ADMIN, foundUser.getRole())) {
            throw new UserException(format("user with email: {0} can not perform operation with admin", operationAuthor.getEmail()));
        }
        //operator can not upd operator
        if (Objects.equals(UserRole.ROLE_ADOPS, foundUser.getRole()) &&
                Objects.equals(UserRole.ROLE_ADOPS, operationAuthor.getRole())) {
            throw new UserException(format("user with email: {0} can not perform operation with operator", operationAuthor.getEmail()));
        }
    }
}
