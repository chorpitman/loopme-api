package com.loopme.api.controller.dto.app;

import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
public class AppCreateDto {
    @NotNull
    @NotBlank(message = "app name can not be blank")
    @Size(message = "app name size must be between 3 and 100 characters", min = 4, max = 100)
    private String name;

    @NotNull
    private AppType type;

    @NotNull
    private Set<ContentType> contentTypes;
}
