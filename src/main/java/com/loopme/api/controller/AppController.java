package com.loopme.api.controller;

import com.loopme.api.controller.dto.app.AppCreateDto;
import com.loopme.api.controller.dto.app.AppDto;
import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;
import com.loopme.api.service.AppService;
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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/api")
public class AppController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    private final AppService appService;

    @Autowired
    public AppController(final AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = "/app", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<AppDto> createApp(@RequestBody @Valid final AppCreateDto app) {
        LOGGER.info(">>> About process create app: '{}'", app);
        AppDto createdApp = appService.createApp(app);
        if (Objects.isNull(createdApp)) {
            LOGGER.error(">>> Unable to create app: '{}'", app);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(createdApp);
    }

    @RequestMapping(value = "/app/{id}", method = RequestMethod.PUT)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<AppDto> updateApp(@PathVariable("id") final Long id, @RequestBody @Valid final AppCreateDto app) {
        LOGGER.info(">>> About process update app: '{}'", app);
        AppDto updatedApp = appService.updateApp(id, app);
        if (Objects.isNull(updatedApp)) {
            LOGGER.error(">>> Unable to update. App with id: {}  does not exist. Or problem with permission", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedApp);
    }

    @RequestMapping(value = "/app/{id}", method = RequestMethod.DELETE)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<Void> deleteApp(@PathVariable("id") final Long id) {
        LOGGER.info(">>> About process delete app by id: '{}'", id);
        if (appService.delete(id)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        LOGGER.error(">>> Unable to delete. App with id: {} not found.", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(value = "/app/{id}", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<AppDto> getAppById(@PathVariable("id") final Long id) {
        LOGGER.info(">>> About process get app by id: '{}'", id);
        AppDto app = appService.findById(id);
        if (Objects.isNull(app)) {
            LOGGER.error(">>> App with id {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(app);
    }

    @RequestMapping(value = "/app/", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<Collection<AppDto>> getApps() {
        LOGGER.info(">>> About process get apps");
        Set<AppDto> apps = appService.findAll();
        if (apps.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }

    @RequestMapping(value = "/app/type/", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<Collection<AppType>> getAppType() {
        LOGGER.info(">>> About process get app types");
        List<AppType> appType = appService.getAppType();
        if (appType.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(appType);
    }

    @RequestMapping(value = "/app/content/", method = RequestMethod.GET)
    @PreAuthorize(value = "hasAnyRole('PUBLISHER, ADOPS')")
    public ResponseEntity<Collection<ContentType>> getAppContentType() {
        LOGGER.info(">>> About process get app content type");
        List<ContentType> contentType = appService.getContentType();
        if (contentType.size() == 0) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(contentType);
    }
}
