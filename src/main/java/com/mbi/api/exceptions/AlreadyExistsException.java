package com.mbi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlreadyExistsException extends Exception {

    private Class entityClassName;

    public AlreadyExistsException(Class entity) {
        this.entityClassName = entity;
    }

    @Override
    public String getMessage() {
        return getEntityClassName().toUpperCase() + " ALREADY EXISTS";
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
