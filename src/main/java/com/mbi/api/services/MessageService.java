package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.AttachmentFactory;
import com.mbi.api.repositories.SlackRepository;
import com.mbi.api.repositories.TestCaseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        final var json = new JSONObject(payload);
        final var actionName = json.getJSONArray("actions").getJSONObject(0).getString("name");
        final var messageTimeStamp = json.getString("message_ts");
        System.out.println(json.toString());
        // Show defects button
        if ("hide_defects".equals(actionName)) {
            hideTestCases(messageTimeStamp);
        }
        // Hide defects button
        if ("show_defects".equals(actionName)) {
            showTestCases(messageTimeStamp);
        }
        // Show stacktrace button
        if ("show_stacktrace".equals(actionName)) {
            final int callbackId = Integer.parseInt(json.getString("callback_id"));
            final String channelId = json.getJSONObject("user").getString("id");
            sendStackTrace(callbackId, channelId);
        }
    }

    public MessageEntity createSlackMessage(final int testRunId) throws NotFoundException, JsonProcessingException,
            BadRequestException {
        final var testRun = testRunService.getTestRunById(testRunId);
        final var testRunDiff = testRunService.getBuildDifference(testRunId);

        // Add attachments
        final List<Attachment> attachments = new ArrayList<>();
        // Main attachment
        final var mainAttachment = new AttachmentFactory().getMain(testRun, testRunDiff);
        attachments.add(mainAttachment);
        // Actions attachment
        if (!testRun.isSuccessful()) {
            final var actionsAttachment = new AttachmentFactory().getAction();
            attachments.add(actionsAttachment);
        }

        // Send message
        final var slackResponse = slackService.sendSlackMessage(attachments);

        // Save message if no errors
        if (!slackResponse.isOk()) {
            throw new BadRequestException(MessageEntity.class, slackResponse.getError());
        }
        final var messageEntity = mapper.map(slackResponse, MessageEntity.class);
        slackRepository.save(messageEntity);

        return messageEntity;
    }

    private void showTestCases(final String messageTimeStamp) throws NotFoundException, IOException {
        // Get test run id
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var testRunId = getTestRunIdFromMessage(message);

        // Get test cases
        final var testCases = testCaseService.getMethodsByStatus(testRunId, MethodStatus.FAILED, PageRequest.of(0, 10));

        final var attachmentList = getAttachmentsFromMessage(message);
        final var attachmentFactory = new AttachmentFactory();
        // Add test cases
        for (var testCase : testCases.getContent()) {
            final var attachment = attachmentFactory.getDefect(testCase);
            attachmentList.add(attachment);
        }
        // Add test cases pagination
        var pagination = attachmentFactory.getPagination();
        attachmentList.add(pagination);

        // Send
        slackService.updateSlackMessage(attachmentList, messageTimeStamp);
    }

    private void hideTestCases(final String messageTimeStamp) throws NotFoundException, IOException {
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var attachmentList = getAttachmentsFromMessage(message);
        // Remove test cases
        for (var attachment : attachmentList) {
            if (attachment.getFallback().equals("defect")) {
                attachmentList.remove(attachment);
            }
        }

        // Send
        slackService.updateSlackMessage(attachmentList, messageTimeStamp);
    }

    private void sendStackTrace(final int callbackId, final String channelId) throws NotFoundException,
            JsonProcessingException {
        final var testCase = testCaseRepository.findById(callbackId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var attachment = new AttachmentFactory().getStackTrace(testCase.getName(), testCase.getException());

        slackService.sendSlackMessage(channelId, List.of(attachment));
    }

    private int getTestRunIdFromMessage(final MessageEntity messageEntity) throws JsonProcessingException {
        final var messageJson = new JSONObject(objectToString(messageEntity)).getJSONObject("message");

        return Integer.parseInt(messageJson
                .getJSONArray("attachments")
                .getJSONObject(0)
                .getString("fallback")
                .split("=")[1]);
    }

    private List<Attachment> getAttachmentsFromMessage(final MessageEntity messageEntity) throws IOException {
        final var attachments = new JSONObject(objectToString(messageEntity))
                .getJSONObject("message")
                .getJSONArray("attachments");

        final List<Attachment> list = new ArrayList<>();
        for (var attach : attachments) {
            final var attachment = stringToObject(attach.toString(), Attachment.class);
            list.add(attachment);
        }

        return list;
    }
}
