package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "test")
public class TestModel {

    private String name;

    private String duration;

    private Set<ClassModel> classes;

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

    @XmlElement(name = "class")
    public Set<ClassModel> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassModel> classes) {
        this.classes = classes;
    }
}
