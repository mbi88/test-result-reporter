package com.mbi.api.models.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(name = "testng-results")
public class TestRunModel {

    private String total;

    private String passed;

    private String failed;

    private String skipped;

    private String ignored;

    private Set<SuiteModel> suites;

    @XmlAttribute
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @XmlAttribute
    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    @XmlAttribute
    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    @XmlAttribute
    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    @XmlAttribute
    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    @XmlElement(name = "suite")
    public Set<SuiteModel> getSuites() {
        return suites;
    }

    public void setSuites(Set<SuiteModel> suites) {
        this.suites = suites;
    }
}
