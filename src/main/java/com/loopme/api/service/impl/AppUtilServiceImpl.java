package com.loopme.api.service.impl;

import com.loopme.api.exception.AppException;
import com.loopme.api.model.App;
import com.loopme.api.service.AppUtilService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.text.MessageFormat.format;

@Service
public class AppUtilServiceImpl implements AppUtilService {
    @Override
    public void nullCheck(final App app, final Long appId) {
        if (Objects.isNull(app)) {
            throw new AppException(format("user with id: {0} does not exist", appId));
        }
    }
}
