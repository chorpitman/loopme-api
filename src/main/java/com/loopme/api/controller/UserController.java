package com.loopme.api.controller;

import com.loopme.api.controller.dto.SignUpRequest;
import com.loopme.api.controller.dto.UserDto;
import com.loopme.api.controller.dto.UserUpdDto;
import com.loopme.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('ADMIN, ADOPS')")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid final SignUpRequest user) {
        LOGGER.info(">>> About registration process '{}'", user);

        UserDto createdUser = userService.createUser(user);
        if (Objects.isNull(createdUser)) {
            LOGGER.error(">>> Unable to create. A User with name: {} already exist", user.getName());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('ADMIN, ADOPS')")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") final Long id, @RequestBody @Valid final UserUpdDto user) {
        LOGGER.info(">>> About update process '{}'", user);

        UserDto updatedUser = userService.update(id, user);
        if (Objects.isNull(updatedUser)) {
            LOGGER.error(">>> Unable to update. User with id: {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @PreAuthorize(value = "hasAnyRole('ADMIN, ADOPS')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long id) {
        LOGGER.info(">>> About user delete process by id: '{}'", id);

        if (userService.delete(id)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        LOGGER.error(">>> Unable to delete. User with id: {} not found.", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN, ADOPS')")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") final Long id) {
        LOGGER.info(">>> About get user by id: '{}'", id);
        UserDto user = userService.findById(id);
        if (Objects.isNull(user)) {
            LOGGER.error(">>> User with id {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('ADMIN, ADOPS')")
    public ResponseEntity<List<UserDto>> getUsers() {
        LOGGER.info(">>> About get all users");
        List<UserDto> users = userService.findAll();
        if (users.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
