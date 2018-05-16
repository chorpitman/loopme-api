package com.loopme.api.converter;

import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.model.App;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<AppDto> convert(final List<App> apps) {
        List<AppDto> appDtoList = new ArrayList<>();
        for (App app : apps) {
            AppDto converted = convert(app);
            appDtoList.add(converted);
        }

        return appDtoList;
    }
}
