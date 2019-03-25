package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "suite")
public class SuiteModel {

    private String name;

    private String duration;

    private Set<TestModel> tests;

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

    @XmlElement(name = "test")
    public Set<TestModel> getTests() {
        return tests;
    }

    public void setTests(Set<TestModel> tests) {
        this.tests = tests;
    }
}