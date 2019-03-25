package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "exception")
public class ExceptionModel {

    private String exception;

    @XmlElement(name = "full-stacktrace")
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}