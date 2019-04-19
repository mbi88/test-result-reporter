package com.mbi.api.models.request.slack;

import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;

import java.util.List;

/**
 * Attachment mapper.
 */
public class AttachmentFactory {

    public Attachment getMain(final TestRunResponse testRun, final TestRunDeltaResponse testRunDiff) {
        final var attachment = new Attachment();
        attachment.setAuthorName(testRun.getProductName().toUpperCase());
        attachment.setFallback("testRunId=" + testRun.getId());
        attachment.setFooter(getDuration(testRun.getDuration(), testRunDiff.getDurationDiff()));
        attachment.setTs(Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10)));
        attachment.setColor(testRun.isSuccessful() ? "#85d196" : "#d18585");
        // Fields
        final var fieldFormat = "%d (%d)";
        final var total = new Field();
        total.setShortField(true);
        total.setTitle("Total");
        total.setValue(String.format(fieldFormat, testRun.getTotal(), testRunDiff.getTotalDiff()));
        final var passed = new Field();
        passed.setShortField(true);
        passed.setTitle("Passed");
        passed.setValue(String.format(fieldFormat, testRun.getPassed(), testRunDiff.getPassedDiff()));
        final var failed = new Field();
        failed.setShortField(true);
        failed.setTitle("Failed");
        failed.setValue(String.format(fieldFormat, testRun.getFailed(), testRunDiff.getFailedDiff()));
        final var skipped = new Field();
        skipped.setShortField(true);
        skipped.setTitle("Skipped");
        skipped.setValue(String.format(fieldFormat, testRun.getSkipped(), testRunDiff.getSkippedDiff()));
        attachment.setFields(List.of(total, passed, failed, skipped));

        return attachment;
    }

    public Attachment getAction() {
        final var attachment = new Attachment();
        attachment.setFallback("Actions");
        attachment.setColor("#c6c6c6");
        attachment.setTitle("Actions");
        attachment.setCallbackId("show_defects_actions");
        attachment.setShortField(false);
        attachment.setAttachmentType("default");
        // Actions
        final var show = new Action();
        show.setName("show_defects");
        show.setText("Show Failed Tests");
        show.setType("button");
        show.setValue("defects");
        show.setFallback("show_defects");
        final var hide = new Action();
        hide.setName("hide_defects");
        hide.setText("Hide Failed Tests");
        hide.setType("button");
        hide.setValue("defects");
        hide.setFallback("hide_defects");
        attachment.setActions(List.of(show, hide));

        return attachment;
    }

    private String getDuration(final int testRunDuration, final int durationDiff) {
        final var testRunDurationString = formatDateTime(testRunDuration);
        final var durationDiffString = formatDateTime(durationDiff);

        return String.format("Duration: %s (%s)", testRunDurationString, durationDiffString);
    }

    private String formatDateTime(final long millis) {
        final long seconds = (Math.abs(millis) / 1000) % 60;
        final long minutes = (Math.abs(millis) / (1000 * 60)) % 60;
        final long hours = (Math.abs(millis) / (1000 * 60 * 60)) % 24;

        final String timeFormat;

        if (hours > 0) {
            timeFormat = String.format("%dh %dm %ds", hours, minutes, seconds);
        } else {
            if (minutes > 0) {
                timeFormat = String.format("%dm %ds", minutes, seconds);
            } else {
                timeFormat = String.format("%ds", seconds);
            }
        }

        return millis >= 0 ? timeFormat : "-".concat(timeFormat);
    }
}
