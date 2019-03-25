package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "suite")
public class SuiteModel {

    private String name;

    private String duration;

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    @XmlAttribute(name = "duration-ms")
    public String getDuration() {
        return duration;
    }
}
