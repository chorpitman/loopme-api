package com.loopme.api.controller.dto.app;

import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class AppDto {
    private Long id;
    private String name;
    private AppType type;
    private Set<ContentType> contentTypes;
}
