package com.mbi.api.models.request.testng;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Exception request model.
 */
@XmlRootElement(name = "exception")
public class ExceptionModel {

    private String exception;

    @XmlElement(name = "full-stacktrace")
    public String getException() {
        return exception;
    }

    public void setException(final String exception) {
        this.exception = exception;
    }
}
