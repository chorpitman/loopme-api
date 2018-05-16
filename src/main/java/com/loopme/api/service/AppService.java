package com.loopme.api.service;

import com.loopme.api.controller.dto.app.AppCreateDto;
import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;

import java.util.List;
import java.util.Set;

public interface AppService {
    AppDto createApp(final AppCreateDto appDto);

    AppDto updateApp(final Long appId, final AppCreateDto appDto);

    Boolean delete(final Long userId);

    AppDto findById(Long userId);

    Set<AppDto> findAll();

    List<ContentType> getContentType();

    List<AppType> getAppType();
}