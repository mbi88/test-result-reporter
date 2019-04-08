package com.mbi.api.models.response;

/**
 * Statistics difference.
 */
public class TestRunDeltaResponse {

    private String durationDiff;

    private String totalDiff;

    private String passedDiff;

    private String failedDiff;

    private String skippedDiff;

    public String getDurationDiff() {
        return durationDiff;
    }

    public void setDurationDiff(final String durationDiff) {
        this.durationDiff = durationDiff;
    }

    public String getTotalDiff() {
        return totalDiff;
    }

    public void setTotalDiff(final String totalDiff) {
        this.totalDiff = totalDiff;
    }

    public String getPassedDiff() {
        return passedDiff;
    }

    public void setPassedDiff(final String passedDiff) {
        this.passedDiff = passedDiff;
    }

    public String getFailedDiff() {
        return failedDiff;
    }

    public void setFailedDiff(final String failedDiff) {
        this.failedDiff = failedDiff;
    }

    public String getSkippedDiff() {
        return skippedDiff;
    }

    public void setSkippedDiff(final String skippedDiff) {
        this.skippedDiff = skippedDiff;
    }
}
