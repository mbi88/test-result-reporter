package com.mbi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistsException extends Exception {

    private Class entityClassName;
    private String message;

    public AlreadyExistsException(Class entity, String message) {
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
        return "Entity Already Exists";
    }
}
