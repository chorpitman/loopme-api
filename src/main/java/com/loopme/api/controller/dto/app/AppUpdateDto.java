package com.loopme.api.controller.dto.app;

import com.loopme.api.model.AppType;
import com.loopme.api.model.ContentType;

import java.util.Set;

public class AppUpdateDto {
    private String name;
    private AppType type;
    private Set<ContentType> contentTypes;
}
