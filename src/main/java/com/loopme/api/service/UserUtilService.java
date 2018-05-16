package com.loopme.api.service;

import com.loopme.api.model.User;

public interface UserUtilService {
    void nullCheck(final User user, final Long userId);

    void nullCheck(final User user, final String userEmail);
}
