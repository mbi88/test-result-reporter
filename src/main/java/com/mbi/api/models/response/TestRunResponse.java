package com.mbi.api.models.response;

/**
 * Test run response model.
 */
public class TestRunResponse {

    private long id;

    private int total;

    private int passed;

    private int failed;

    private int skipped;

    private int ignored;

    private String productName;

    private int duration;

    private boolean successful;

    private String createdAt;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(final int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(final int failed) {
        this.failed = failed;
    }

    public int getSkipped() {
        return skipped;
    }

    public void setSkipped(final int skipped) {
        this.skipped = skipped;
    }

    public int getIgnored() {
        return ignored;
    }

    public void setIgnored(final int ignored) {
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

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(final boolean successful) {
        this.successful = successful;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }
}
