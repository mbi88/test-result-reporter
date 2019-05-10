package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.enums.DefectsPage;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.BlocksFactory;
import com.mbi.api.repositories.SlackRepository;
import com.mbi.api.repositories.TestCaseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

/**
 * Slack message service.
 */
@Service
@SuppressWarnings({"MultipleStringLiterals", "PMD.SystemPrintln", "PMD.AvoidDuplicateLiterals"})
public class MessageService extends BaseService {

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private SlackRepository slackRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private SlackService slackService;

    public void interactWithSlack(final String payload) throws NotFoundException, IOException {
        final var json = new JSONObject(payload);
        final var actionName = json.getJSONArray("actions").getJSONObject(0).getString("action_id");
        final var messageTimeStamp = json.getJSONObject("message").getString("ts");
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));

        //  Hide defects button
        if ("hide_failed_tests".equals(actionName)) {
            hideTestCases(message);
        }

        // Show defects button
        if ("show_failed_tests".equals(actionName)) {
            showTestCases(message, DefectsPage.DEFAULT);
        }

        // Show stacktrace button
        if ("show_stack_trace".equals(actionName)) {
            final int defectId = Integer.parseInt(json.getJSONArray("actions").getJSONObject(0)
                    .getString("block_id").split("defect_test_")[1]);
            final String channelId = json.getJSONObject("user").getString("id");
            sendStackTrace(defectId, channelId);
        }

        // Show next defects
        if ("next_tests".equals(actionName)) {
            showTestCases(message, DefectsPage.NEXT);
        }

        // Show previous defects
        if ("prev_tests".equals(actionName)) {
            showTestCases(message, DefectsPage.PREVIOUS);
        }
    }

    public MessageEntity createSlackMessage(final int testRunId) throws NotFoundException, JsonProcessingException,
            BadRequestException {
        final var testRun = testRunService.getTestRunById(testRunId);
        final var testRunDiff = testRunService.getBuildDifference(testRunId);

        // Add blocks
        final List<Object> blocks = new BlocksFactory().getMainMessage(testRun, testRunDiff);

        // Send message
        final var slackResponse = slackService.sendSlackMessage(blocks);

        // Save message if no errors
        if (!slackResponse.isOk()) {
            throw new BadRequestException(MessageEntity.class, slackResponse.getError());
        }
        final var messageEntity = mapper.map(slackResponse, MessageEntity.class);
        messageEntity.setCurrentPage(0);
        messageEntity.setTestRunId(testRunId);
        slackRepository.save(messageEntity);

        return messageEntity;
    }

    private void showTestCases(final MessageEntity message, final DefectsPage defectsPage) throws NotFoundException,
            IOException {
        // Find what page to show
        int page = 0;
        var testCases = testCaseService.getMethodsByStatus(
                message.getTestRunId(),
                MethodStatus.FAILED,
                PageRequest.of(page, 10, Sort.by("id")));
        final int currentPage = message.getCurrentPage();
        final int totalPages = testCases.getTotalPages();
        switch (defectsPage) {
            case NEXT: {
                if ((currentPage + 1) < totalPages) {
                    page = currentPage + 1;
                }
                break;
            }
            case PREVIOUS: {
                if (currentPage > 0) {
                    page = currentPage - 1;
                } else {
                    page = currentPage;
                }
                break;
            }
            case DEFAULT: {
                page = 0;
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + defectsPage);
        }

        // Get test cases
        testCases = testCaseService.getMethodsByStatus(
                message.getTestRunId(),
                MethodStatus.FAILED,
                PageRequest.of(page, 10, Sort.by("id")));

        final var testRun = testRunService.getTestRunById(message.getTestRunId());
        final var testRunDiff = testRunService.getBuildDifference(message.getTestRunId());

        // Add main part of message
        final var blockFactory = new BlocksFactory();
        final List<Object> blocksList = blockFactory.getMainMessage(testRun, testRunDiff);
        // Add test cases
        for (var testCase : testCases.getContent()) {
            final var defectBlock = blockFactory.getDefect(testCase);
            blocksList.add(defectBlock);
        }
        // Add test cases pagination
        var paginationLabel = blockFactory.getPaginationLabel(page, totalPages);
        blocksList.add(paginationLabel);
        var paginationButtons = blockFactory.getPaginationButtons();
        blocksList.add(paginationButtons);

        // Send
        slackService.updateSlackMessage(blocksList, message.getTs());

        // Save current page
        message.setCurrentPage(page);
        slackRepository.save(message);
    }

    private void hideTestCases(final MessageEntity message) throws NotFoundException, IOException {
        final var testRun = testRunService.getTestRunById(message.getTestRunId());
        final var testRunDiff = testRunService.getBuildDifference(message.getTestRunId());

        // Add main part of message
        final List<Object> blocksList = new BlocksFactory().getMainMessage(testRun, testRunDiff);

        // Send
        slackService.updateSlackMessage(blocksList, message.getTs());
    }

    private void sendStackTrace(final int defectId, final String channelId) throws NotFoundException,
            JsonProcessingException {
        final var testCase = testCaseRepository.findById(defectId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var block = new BlocksFactory().getStackTrace(
                String.format("%s.%s", testCase.getClassName(), testCase.getName()),
                testCase.getException());

        slackService.sendSlackMessage(channelId, List.of(block));
    }
}
