package com.loopme.api.controller.dto;

import com.loopme.api.model.App;
import com.loopme.api.model.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Set<App> apps;
}
