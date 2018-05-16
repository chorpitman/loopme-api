package com.loopme.api.service;

import com.loopme.api.controller.dto.app.AppCreateDto;
import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;

import java.util.List;

public interface AppService {
    AppDto createApp(final AppCreateDto appDto);

    AppDto updateApp(final Long appId, final AppCreateDto appDto);

    List<ContentType> getContentType();

    List<AppType> getAppType();
}