package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.message.MessageEntity;
import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.enums.DefectsPage;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.response.TestCaseResponse;
import com.mbi.api.repositories.MessageRepository;
import com.mbi.api.repositories.TestCaseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.mbi.api.exceptions.ExceptionSupplier.*;

/**
 * Slack message service.
 */
@Service
public class MessageService extends BaseService {

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private SlackService slackService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    public void interactWithSlack(final String payloadString) throws NotFoundException, IOException {
        final var payload = new JSONObject(payloadString);
        final var actionName = payload.getJSONArray("actions").getJSONObject(0).getString("action_id");
        final var messageTimeStamp = payload.getJSONObject("message").getString("ts");
        final var message = messageRepository.findByTs(messageTimeStamp)
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
            final int defectId = Integer.parseInt(payload.getJSONArray("actions").getJSONObject(0)
                    .getString("block_id").split("defect_test_")[1]);
            final String channelId = payload.getJSONObject("user").getString("id");
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
            throw BAD_REQUEST_SUPPLIER.apply(ProductEntity.class, slackResponse.getError()).get();
        }
        final var messageEntity = mapper.map(slackResponse, MessageEntity.class);
        messageEntity.setCurrentPage(0);
        messageEntity.setTestRunId(testRunId);
        messageRepository.save(messageEntity);

        return messageEntity;
    }

    private void showTestCases(final MessageEntity message, final DefectsPage defectsPage) throws NotFoundException,
            IOException {
        // Get test cases
        final var testCases = getTestCases(message, defectsPage);

        final var testRun = testRunService.getTestRunById(message.getTestRunId());
        final var testRunDiff = testRunService.getBuildDifference(message.getTestRunId());

        // Add main part of message
        final var blockFactory = new BlocksFactory();
        final var blocksList = blockFactory.getMainMessage(testRun, testRunDiff);
        // Add defects part of message
        blocksList.addAll(blockFactory.getDefectsMessage(testCases));

        // Send
        slackService.updateSlackMessage(blocksList, message.getTs());

        // Save current page
        message.setCurrentPage(testCases.getPageable().getPageNumber());
        messageRepository.save(message);
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

    private Page<TestCaseResponse> getTestCases(final MessageEntity message, final DefectsPage defectsPage)
            throws NotFoundException {
        Pageable pageable = PageRequest.of(message.getCurrentPage(), 10, Sort.by("id"));
        switch (defectsPage) {
            case NEXT: {
                pageable = pageable.next();
                break;
            }
            case PREVIOUS: {
                pageable = pageable.previousOrFirst();
                break;
            }
            case DEFAULT: {
                pageable = PageRequest.of(0, 10, Sort.by("id"));
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + defectsPage);
        }

        return testCaseService.getMethodsByStatus(message.getTestRunId(), MethodStatus.FAILED, pageable);
    }
}
