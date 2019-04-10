package com.mbi.api.models.request.testng;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

/**
 * Test request model.
 */
@XmlRootElement(name = "test")
public class TestModel {

    private String name;

    private String duration;

    private Set<ClassModel> classes;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @XmlAttribute(name = "duration-ms")
    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }

    @XmlElement(name = "class")
    public Set<ClassModel> getClasses() {
        return classes;
    }

    public void setClasses(final Set<ClassModel> classes) {
        this.classes = classes;
    }
}
