package com.mbi.api.models.response;

/**
 * Statistics difference.
 */
public class TestRunDeltaResponse {

    private int durationDiff;

    private int totalDiff;

    private int passedDiff;

    private int failedDiff;

    private int skippedDiff;

    public int getDurationDiff() {
        return durationDiff;
    }

    public void setDurationDiff(final int durationDiff) {
        this.durationDiff = durationDiff;
    }

    public int getTotalDiff() {
        return totalDiff;
    }

    public void setTotalDiff(final int totalDiff) {
        this.totalDiff = totalDiff;
    }

    public int getPassedDiff() {
        return passedDiff;
    }

    public void setPassedDiff(final int passedDiff) {
        this.passedDiff = passedDiff;
    }

    public int getFailedDiff() {
        return failedDiff;
    }

    public void setFailedDiff(final int failedDiff) {
        this.failedDiff = failedDiff;
    }

    public int getSkippedDiff() {
        return skippedDiff;
    }

    public void setSkippedDiff(final int skippedDiff) {
        this.skippedDiff = skippedDiff;
    }
}
