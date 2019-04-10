package com.mbi.api.models.request;

/**
 * Test run request model.
 */
public class TestRunModel {

    private int total;

    private int passed;

    private int failed;

    private int skipped;

    private int ignored;

    private int duration;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
