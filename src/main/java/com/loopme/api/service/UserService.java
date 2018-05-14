package com.loopme.api.service;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import org.springframework.security.core.context.SecurityContextHolder;

public interface UserService {
    String getCurrentAuthUser();

    UserDto createUser(final SignUpRequest signUpRequest);

    UserDto update(final UserDto userDto);
}
