package com.loopme.api.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {
    private static final String regExp = "^.+@.+\\..+$";

    @NotBlank
    @NotNull
    @Size(message = "user name size must be between 3 and 100 characters", min = 4, max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Email(message = "please provide a valid email address", regexp = regExp)
    @Size(message = "user email size must be between 5 and 100 characters", min = 3, max = 100)
    private String email;

    @NotNull
    @NotBlank(message = "Please enter your password")
    @Size(message = "user password lengths must be higher or equals 8", min = 6, max = 255)
    private String password;
}
