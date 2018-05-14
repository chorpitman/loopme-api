package com.loopme.api.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdDto {
    @Size(max = 100)
    private String name;
    @Size(max = 100)
    private String email;
}
