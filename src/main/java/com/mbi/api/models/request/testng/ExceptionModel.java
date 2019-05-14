package com.mbi.api.models.request.testng;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Exception request model.
 */
@XmlRootElement(name = "exception")
public class ExceptionModel {

    private String stacktrace;

    @XmlElement(name = "full-stacktrace")
    public String getStacktrace() {
        System.out.println("??? " + stacktrace);
        return stacktrace;
    }

    public void setStacktrace(final String stacktrace) {
        System.out.println("!!! " + stacktrace);
        this.stacktrace = stacktrace;
    }
}
