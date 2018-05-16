package com.loopme.api.converter;

import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserDto> convert(final List<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            UserDto convertedUser = convert(user);
            userDtoList.add(convertedUser);
        }

        return userDtoList;
    }
}
