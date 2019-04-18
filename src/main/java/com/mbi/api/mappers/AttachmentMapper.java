package com.mbi.api.mappers;

import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.Field;
import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;

import java.util.List;

/**
 * Attachment mapper.
 */
public class AttachmentMapper {

    public Attachment map(final TestRunResponse testRun, final TestRunDeltaResponse testRunDiff) {
        var attachment = new Attachment();
        attachment.setAuthorName(testRun.getProductName().toUpperCase());
        attachment.setFallback("testRunId=" + testRun.getId());
        attachment.setFooter("Duration: " + testRun.getDuration());
        attachment.setFooter(String.format("Duration: %d (%d)", testRun.getDuration(), testRunDiff.getDurationDiff()));
        attachment.setTs(Long.parseLong(String.valueOf(System.currentTimeMillis()).substring(0, 10)));
        attachment.setColor(testRun.isSuccessful() ? "#85d196" : "#d18585");
        // Fields
        var total = new Field();
        total.setShortField(true);
        total.setTitle("Total");
        total.setValue(String.format("%d (%d)", testRun.getTotal(), testRunDiff.getTotalDiff()));
        var passed = new Field();
        passed.setShortField(true);
        passed.setTitle("Passed");
        passed.setValue(String.format("%d (%d)", testRun.getPassed(), testRunDiff.getPassedDiff()));
        var failed = new Field();
        failed.setShortField(true);
        failed.setTitle("Failed");
        failed.setValue(String.format("%d (%d)", testRun.getFailed(), testRunDiff.getFailedDiff()));
        var skipped = new Field();
        skipped.setShortField(true);
        skipped.setTitle("Skipped");
        skipped.setValue(String.format("%d (%d)", testRun.getSkipped(), testRunDiff.getSkippedDiff()));
        attachment.setFields(List.of(total, passed, failed, skipped));

        return attachment;
    }
}
