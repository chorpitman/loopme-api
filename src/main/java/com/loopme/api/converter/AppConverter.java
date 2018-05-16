package com.loopme.api.converter;

import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.model.App;
import org.springframework.stereotype.Component;

@Component
public class AppConverter {
    public AppDto convert(final App app) {
        return AppDto.builder()
                .id(app.getId())
                .name(app.getName())
                .type(app.getType())
                .contentTypes(app.getContentTypes())
                .build();
    }
}
