package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "test-method")
public class MethodModel {

    private String name;

    private String duration;

    private String status;

    private ExceptionModel exception;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "duration-ms")
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @XmlElement(name = "exception")
    public ExceptionModel getException() {
        return exception;
    }

    public void setException(ExceptionModel exception) {
        this.exception = exception;
    }

    @XmlAttribute
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
