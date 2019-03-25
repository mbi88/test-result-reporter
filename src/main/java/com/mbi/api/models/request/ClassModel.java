package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "class")
public class ClassModel {

    private String name;

    private Set<MethodModel> methods;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "test-method")
    public Set<MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(Set<MethodModel> methods) {
        this.methods = methods;
    }
}
