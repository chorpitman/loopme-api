package com.loopme.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {UserException.class})
    protected ResponseEntity<Object> handleUserConflict(RuntimeException ex, WebRequest request) {
        LOGGER.error("404 Status Code " + ex.getMessage());
        GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getClass().getSimpleName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {AppException.class})
    protected ResponseEntity<Object> handleAppConflict(RuntimeException ex, WebRequest request) {
        LOGGER.error("404 Status Code " + ex.getMessage());
        GenericResponse bodyOfResponse = new GenericResponse(ex.getMessage(), ex.getClass().getSimpleName());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

//    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
//    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
//        String bodyOfResponse = "This should be application specific";
//
//        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
}
