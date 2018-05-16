package com.loopme.api.service;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.controller.dto.UserUpdDto;

import java.util.List;

public interface UserService {

    UserDto createUser(final SignUpRequest signUpRequest);

    UserDto update(final Long userId, final UserUpdDto userDto);

    Boolean delete(final Long userId);

    UserDto findById(final Long userId);

    List<UserDto> findAll();
}
