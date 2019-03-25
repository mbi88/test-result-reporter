package com.mbi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 * Bad request exception.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    private final Class entityClassName;
    private final String message;

    public BadRequestException(final Class entity, final String message) {
        this.entityClassName = entity;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return getEntityClassName() + ": " + message;
    }

    private String getEntityClassName() {
        return entityClassName.getSimpleName();
    }

    public String getException() {
        return Arrays.toString(getStackTrace());
    }

    public String getError() {
        return "Bad request";
    }
}
