package com.mbi.api.models.request.slack;

import com.mbi.api.models.response.TestCaseResponse;
import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BlocksFactory {

    public List<Object> getMainMessage(final TestRunResponse testRun, final TestRunDeltaResponse testRunDiff) {
        final List<Object> blocks = new ArrayList<>();

        // Tested product name
        blocks.add(getProductNameBlock(testRun));
        // Statistics
        blocks.add(getStatsBlock(testRun, testRunDiff));
        // Context
        blocks.add(getContext(testRun, testRunDiff));
        // Actions
        if (!testRun.isSuccessful()) {
            blocks.add(getActions());
        }
        return blocks;
    }

    public SectionBlock getProductNameBlock(final TestRunResponse testRun) {
        final var text = new Text();
        text.setType("mrkdwn");
        text.setText(String.format("*%s*", testRun.getProductName().toUpperCase()));

        final var block = new SectionBlock();
        block.setType("section");
        block.setText(text);

        return block;
    }

    public SectionBlock getStatsBlock(final TestRunResponse testRun, final TestRunDeltaResponse testRunDiff) {
        final var totalField = new Text();
        totalField.setType("mrkdwn");
        totalField.setText(String.format("*Total*%n%d (%d)", testRun.getTotal(), testRunDiff.getTotalDiff()));
        final var passedField = new Text();
        passedField.setType("mrkdwn");
        passedField.setText(String.format("*Passed*%n%d (%d)", testRun.getPassed(), testRunDiff.getPassedDiff()));
        final var failedField = new Text();
        failedField.setType("mrkdwn");
        failedField.setText(String.format("*Failed*%n%d (%d)", testRun.getFailed(), testRunDiff.getFailedDiff()));
        final var skippedField = new Text();
        skippedField.setType("mrkdwn");
        skippedField.setText(String.format("*Skipped*%n%d (%d)", testRun.getSkipped(), testRunDiff.getSkippedDiff()));

        final var failedColor = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/25/Red.svg/240px-Red.svg.png";
        final var successColor = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b2/Green_square.svg/"
                + "240px-Green_square.svg.png";

        final var accessory = new ImageAccessory();
        accessory.setType("image");
        accessory.setImageUrl(testRun.isSuccessful() ? successColor : failedColor);
        accessory.setAltText("test run status");

        final var block = new SectionBlock();
        block.setType("section");
        block.setFields(List.of(totalField, passedField, failedField, skippedField));
        block.setAccessory(accessory);

        return block;
    }

    public ContextBlock getContext(final TestRunResponse testRun, final TestRunDeltaResponse testRunDiff) {
        final var text = new TextElement();
        text.setType("mrkdwn");
        text.setText(String.format("%s | %s",
                getDuration(testRun.getDuration(), testRunDiff.getDurationDiff()),
                getCurrentDateTime()));

        final var block = new ContextBlock();
        block.setType("context");
        block.setElements(List.of(text));

        return block;
    }

    public ActionsBlock getActions() {
        final var showButtonText = new Text();
        showButtonText.setType("plain_text");
        showButtonText.setText("Show Failed Tests");
        final var showButton = new ButtonElement();
        showButton.setType("button");
        showButton.setText(showButtonText);
        showButton.setActionId("show_failed_tests");
        final var hideButtonText = new Text();
        hideButtonText.setType("plain_text");
        hideButtonText.setText("Hide Failed Tests");
        final var hideButton = new ButtonElement();
        hideButton.setType("button");
        hideButton.setText(hideButtonText);
        hideButton.setActionId("hide_failed_tests");

        final var block = new ActionsBlock();
        block.setType("actions");
        block.setElements(List.of(showButton, hideButton));

        return block;
    }

    public SectionBlock getDefect(final TestCaseResponse testCase) {
        final var text = new Text();
        text.setType("mrkdwn");
        text.setType(String.format("_%s.%s_", testCase.getClassName(), testCase.getName()));

        final var stackTraceText = new Text();
        stackTraceText.setType("plain_text");
        stackTraceText.setText("Stack Trace");

        final var accessory = new ButtonAccessory();
        accessory.setType("button");
        accessory.setActionId("show_stack_trace");
        accessory.setText(stackTraceText);

        final var block = new SectionBlock();
        block.setType("section");
        block.setText(text);
        block.setAccessory(accessory);
        block.setBlockId("defect");

        return block;
    }

    public ActionsBlock getPagination(final int currentPage, final int totalPages) {
        final var nextButtonText = new Text();
        nextButtonText.setType("plain_text");
        nextButtonText.setType(":arrow_forward:");
        nextButtonText.setEmoji(true);
        final var nextButton = new ButtonElement();
        nextButton.setType("button");
        nextButton.setText(nextButtonText);
        nextButton.setActionId("next_tests");

        final var prevButtonText = new Text();
        prevButtonText.setType("plain_text");
        prevButtonText.setType(":arrow_backward:");
        prevButtonText.setEmoji(true);
        final var prevButton = new ButtonElement();
        prevButton.setType("button");
        prevButton.setText(prevButtonText);
        prevButton.setActionId("prev_tests");

        final var block = new ActionsBlock();
        block.setType("actions");
        block.setElements(List.of(nextButton, prevButton));
        block.setBlockId("defect");

        return block;
    }

    public SectionBlock getStackTrace(final String testCaseName, final String message) {
        final var text = new Text();
        text.setType("mrkdwn");
        text.setType(String.format("*%s*%n%s", testCaseName, message.substring(0, Math.min(2000, message.length()))));

        final var block = new SectionBlock();
        block.setType("section");
        block.setText(text);

        return block;
    }

    private String getCurrentDateTime() {
        final var dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
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
