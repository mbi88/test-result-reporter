package com.mbi.api.models.testrun;

import javax.xml.bind.annotation.*;

//@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testng-results")
public class TestRunModel {

    @XmlAttribute
    public String total;

    @XmlAttribute
    public String passed;

    @XmlAttribute
    public String failed;

    @XmlAttribute
    public String skipped;

    @XmlAttribute
    public String ignored;


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
}
