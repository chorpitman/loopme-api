package com.loopme.api.service.impl;

import com.loopme.api.model.User;
import com.loopme.api.repository.UserRepository;
import com.loopme.api.service.AuthenticationService;
import com.loopme.api.service.UserUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserUtilService userUtilService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User getOperationAuthor() {
        User foundUser = userRepository.findByEmail(getAuthentication().getName());
        userUtilService.nullCheck(foundUser, getAuthentication().getName());

        return foundUser;
    }
}