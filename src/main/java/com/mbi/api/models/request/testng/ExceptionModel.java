package com.mbi.api.models.request.testng;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Exception request model.
 */
@XmlRootElement(name = "exception")
public class ExceptionModel {

    private String stacktrace;

    @XmlElement(name = "full-stacktrace")
    @XmlJavaTypeAdapter(AdapterXmlCDATA.class)
    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(final String stacktrace) {
        this.stacktrace = stacktrace;
    }
}
