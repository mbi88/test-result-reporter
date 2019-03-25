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

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public void setSkipped(String skipped) {
        this.skipped = skipped;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    public void setSuites(Set<SuiteModel> suites) {
        this.suites = suites;
    }

    @XmlAttribute
    public String getTotal() {
        return total;
    }

    @XmlAttribute
    public String getPassed() {
        return passed;
    }

    @XmlAttribute
    public String getFailed() {
        return failed;
    }

    @XmlAttribute
    public String getSkipped() {
        return skipped;
    }

    @XmlAttribute
    public String getIgnored() {
        return ignored;
    }

    @XmlElement(name = "suite")
    public Set<SuiteModel> getSuites() {
        return suites;
    }
}
