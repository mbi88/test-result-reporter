package com.mbi.api.models.response;

/**
 * Test run response model.
 */
public class TestRunResponse {

    private long id;

    private String total;

    private String passed;

    private String failed;

    private String skipped;

    private String ignored;

    private String productName;

    private int duration;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(final String total) {
        this.total = total;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(final String passed) {
        this.passed = passed;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(final String failed) {
        this.failed = failed;
    }

    public String getSkipped() {
        return skipped;
    }

    public void setSkipped(final String skipped) {
        this.skipped = skipped;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(final String ignored) {
        this.ignored = ignored;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
