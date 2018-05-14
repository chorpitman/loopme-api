package com.loopme.api.service;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.controller.dto.UserUpdDto;

public interface UserService {
    String getCurrentAuthUser();

    UserDto createUser(final SignUpRequest signUpRequest);

    UserDto update(final Long userId, final UserUpdDto userDto);

    Boolean delete(final Long userId);
}
