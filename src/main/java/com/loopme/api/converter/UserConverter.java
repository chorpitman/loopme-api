package com.loopme.api.converter;

import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto convert(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .apps(user.getApps())
                .build();
    }
}
