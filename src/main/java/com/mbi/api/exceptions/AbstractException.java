package com.mbi.api.exceptions;

/**
 * Abstract exception.
 */
public abstract class AbstractException extends Exception {
    public abstract String getException();

    public abstract String getError();
}
