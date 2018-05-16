package com.loopme.api.service.impl;

import com.loopme.api.controller.dto.app.AppCreateDto;
import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.converter.AppConverter;
import com.loopme.api.model.App;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import com.loopme.api.model.User;
import com.loopme.api.repository.AppRepository;
import com.loopme.api.repository.UserRepository;
import com.loopme.api.service.AppService;
import com.loopme.api.service.AppUtilService;
import com.loopme.api.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class AppServiceImpl implements AppService {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final AppUtilService appUtilService;
    private final AppRepository appRepository;
    private final AppConverter appConverter;

    @Autowired
    public AppServiceImpl(final AuthenticationService authenticationService, final UserRepository userRepository,
                          final AppUtilService appUtilService, final AppRepository appRepository, final AppConverter appConverter) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.appUtilService = appUtilService;
        this.appRepository = appRepository;
        this.appConverter = appConverter;
    }

    @Transactional
    @Override
    public AppDto createApp(final AppCreateDto appDto) {
        User operationAuthor = userRepository.findByEmail(authenticationService.getAuthentication().getName());
        App app = App.builder()
                .name(appDto.getName())
                .type(appDto.getType())
                .contentTypes(appDto.getContentTypes())
                .user(operationAuthor)
                .build();
        appRepository.save(app);

        return appConverter.convert(app);
    }

    @Transactional
    @Override
    public AppDto updateApp(final Long appId, final AppCreateDto appDto) {
        App foundApp = appRepository.findOne(appId);
        appUtilService.nullCheck(foundApp, appId);
        if (!isAppOwner(foundApp)) {
            return null;
        }
        foundApp.setName(appDto.getName());
        foundApp.setType(appDto.getType());
        foundApp.setContentTypes(appDto.getContentTypes());

        return appConverter.convert(appRepository.save(foundApp));
    }

    @Override
    public Boolean delete(Long userId) {
        App foundApp = appRepository.findOne(userId);
        if (Objects.isNull(foundApp) || !isAppOwner(foundApp)) {
            return Boolean.FALSE;
        }
        userRepository.delete(userId);

        return Boolean.TRUE;
    }

    @Override
    public AppDto findById(Long userId) {
        //todo think about lazy
        App foundApp = appRepository.findOne(userId);
        if (Objects.isNull(foundApp)) {
            return null;
        }
        return appConverter.convert(foundApp);
    }

    @Override
    public List<AppDto> findAll() {
        //todo think about lazy
        List<App> apps = appRepository.findAll();
        if (apps.size() == 0) {
            return Collections.emptyList();
        }

        return appConverter.convert(apps);
    }

    @Override
    public List<ContentType> getContentType() {
        return Arrays.asList(ContentType.values());
    }

    @Override
    public List<AppType> getAppType() {
        return Arrays.asList(AppType.values());
    }

    private boolean isAppOwner(final App app) {
        User operationAuthor = authenticationService.getOperationAuthor();
        return Objects.equals(operationAuthor.getId(), app.getUser().getId());
    }
}
