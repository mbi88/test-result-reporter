package com.mbi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

/**
 * Not found exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends AbstractException {

    private final Class entityClassName;
    private final String message;

    public NotFoundException(final Class entity, final String message) {
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
        return "Entity Not Found";
    }
}
