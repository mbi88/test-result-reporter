package com.mbi.api.models.request.report;

import com.mbi.api.enums.MethodStatus;

/**
 * Test case request model.
 */
public class TestCaseModel {

    private String name;

    private String className;

    private int duration;

    private MethodStatus status;

    private String exception;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public MethodStatus getStatus() {
        return status;
    }

    public void setStatus(final MethodStatus status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(final String exception) {
        this.exception = exception;
    }
}
