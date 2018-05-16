package com.loopme.api.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
class GenericResponse {
    private String message;
    private String error;

    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.error = error;
    }
}

