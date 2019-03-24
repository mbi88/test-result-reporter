package com.mbi.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    private Class entityClassName;

    public NotFoundException(Class model) {
        this.entityClassName = model;
    }

    private String getEntityClassName() {
        return entityClassName.getSimpleName();
    }

    public String getError() {
        return "Not Found";
    }

    public String getException() {
        return Arrays.toString(getStackTrace());
    }

    @Override
    public String getMessage() {
        return getEntityClassName().toUpperCase() + " NOT FOUND";
    }
}
