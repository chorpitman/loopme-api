package com.loopme.api.service;

import com.loopme.api.model.User;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication getAuthentication();

    User getOperationAuthor();
}