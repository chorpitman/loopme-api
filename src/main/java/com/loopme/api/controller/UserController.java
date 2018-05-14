package com.loopme.api.controller;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //create publisher
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid final SignUpRequest user) {
//        LOGGER.debug("About process get user profile by id '{}'", userId);
        String currentAuthUser = userService.getCurrentAuthUser();
        UserDto createdUser = userService.createUser(user);
        if (Objects.isNull(createdUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    //upd publisher
//    @RequestMapping(value = "/user", method = RequestMethod.PUT)
//    public ResponseEntity<Void> updateUser(@RequestBody @Valid final UserDto dto) {
////        LOGGER.debug("About process get user profile by id '{}'", userId);
////        User user = userService.createUser();
//        if (Objects.isNull(user)) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }
}
