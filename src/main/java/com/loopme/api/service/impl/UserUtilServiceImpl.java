package com.loopme.api.service.impl;

import com.loopme.api.exception.UserException;
import com.loopme.api.model.User;
import com.loopme.api.service.UserUtilService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.text.MessageFormat.format;

@Service
public class UserUtilServiceImpl implements UserUtilService {

    @Override
    public void nullCheck(final User user, final Long userId) {
        if (Objects.isNull(user)) {
            throw new UserException(format("user with id: {0} does not exist", userId));
        }
    }

    @Override
    public void nullCheck(User user, String userEmail) {
        if (Objects.isNull(user)) {
            throw new UserException(format("user with email: {0} does not exist", userEmail));
        }
    }
}
