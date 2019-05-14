package com.mbi.api.models.request.testng;

import com.sun.xml.txw2.annotation.XmlCDATA;

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
    @XmlCDATA
    @XmlJavaTypeAdapter(AdapterXmlCDATA.class)
    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(final String stacktrace) {
        this.stacktrace = stacktrace;
    }
}
