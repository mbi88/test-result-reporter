package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.enums.DefectsPage;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Block;
import com.mbi.api.models.request.slack.BlocksFactory;
import com.mbi.api.models.request.slack.SectionBlock;
import com.mbi.api.repositories.SlackRepository;
import com.mbi.api.repositories.TestCaseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
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
        System.out.println(payload);
        final var json = new JSONObject(payload);
        final var actionName = json.getJSONArray("actions").getJSONObject(0).getString("action_id");
        final var messageTimeStamp = json.getJSONObject("message").getString("ts");
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));

        //  Hide defects button
        if ("hide_failed_tests".equals(actionName)) {
            hideTestCases(messageTimeStamp);
        }

        // Show defects button
        if ("show_failed_tests".equals(actionName)) {
            showTestCases(message, DefectsPage.DEFAULT);
        }

        // Show stacktrace button
        if ("show_stack_trace".equals(actionName)) {
            final int callbackId = Integer.parseInt(json.getString("callback_id"));
            final String channelId = json.getJSONObject("user").getString("id");
            sendStackTrace(callbackId, channelId);
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

    public MessageEntity createSlackMessage2(final int testRunId) throws NotFoundException, JsonProcessingException,
            BadRequestException {
        final var testRun = testRunService.getTestRunById(testRunId);
        final var testRunDiff = testRunService.getBuildDifference(testRunId);

        // Add blocks
        final List<Block> blocks = new ArrayList<>();
        final var blockFactory = new BlocksFactory();
        // Tested product name
        final var testedProductName = blockFactory.getProductNameBlock(testRun);
        blocks.add(testedProductName);
        // Statistics
        final var statsBlock = blockFactory.getStatsBlock(testRun, testRunDiff);
        blocks.add(statsBlock);
        // Context
        final var context = blockFactory.getContext(testRun, testRunDiff);
        blocks.add(context);
        // Actions
        if (!testRun.isSuccessful()) {
            final var actions = blockFactory.getActions();
            blocks.add(actions);
        }

        // Send message
        final var slackResponse = slackService.sendSlackMessage(blocks);
        System.out.println(objectToString(blocks));
        System.out.println(objectToString(slackResponse));

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
        final int currentPage = message.getCurrentPage();
        switch (defectsPage) {
            case NEXT: {
                page = currentPage + 1;
                break;
            }
            case PREVIOUS: {
                if (currentPage > 0) {
                    page = currentPage - 1;
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
        final var testCases = testCaseService.getMethodsByStatus(
                message.getTestRunId(),
                MethodStatus.FAILED,
                PageRequest.of(page, 2, Sort.by("id")));

        final List<Object> blocksList = getBlocksFromMessage(message);
        final var blockFactory = new BlocksFactory();
        // Add test cases
        for (var testCase : testCases.getContent()) {
            final var defectBlock = blockFactory.getDefect(testCase);
            blocksList.add(defectBlock);
        }
        // Add test cases pagination
        var pagination = blockFactory.getPagination(page, testCases.getTotalPages());
        blocksList.add(pagination);
        System.out.println(objectToString(blocksList));
        System.out.println(message.getTs());
        // Send
        slackService.updateSlackMessage(blocksList, message.getTs());

        // Save current page
        message.setCurrentPage(page);
        slackRepository.save(message);
    }

    private void hideTestCases(final String messageTimeStamp) throws NotFoundException, IOException {
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var blocks = getBlocksFromMessage(message);
        // Remove test cases
        for (var block : blocks) {
            if (((SectionBlock) block).getBlockId().equals("defect")) {
                blocks.remove(block);
            }
        }

        // Send
        slackService.updateSlackMessage(blocks, messageTimeStamp);
    }

    private void sendStackTrace(final int callbackId, final String channelId) throws NotFoundException,
            JsonProcessingException {
        final var testCase = testCaseRepository.findById(callbackId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var block = new BlocksFactory().getStackTrace(testCase.getName(), testCase.getException());

        slackService.sendSlackMessage(channelId, List.of(block));
    }

    private List<Object> getBlocksFromMessage(final MessageEntity messageEntity) throws IOException {
        final var blocks = new JSONObject(objectToString(messageEntity))
                .getJSONObject("message")
                .getJSONArray("blocks");

        return blocks.toList();
    }
}
