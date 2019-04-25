package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.SlackConfig;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.AttachmentFactory;
import com.mbi.api.models.response.SlackResponse;
import com.mbi.api.repositories.SlackRepository;
import com.mbi.api.repositories.TestCaseRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

/**
 * Slack service.
 */
@Service
@SuppressWarnings({"MultipleStringLiterals", "PMD.SystemPrintln", "PMD.AvoidDuplicateLiterals"})
public class SlackService extends BaseService {

    @Autowired
    private SlackConfig config;

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private SlackRepository slackRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    public void update(final String payload) throws NotFoundException, IOException {
        final var json = new JSONObject(payload);
        final var actionName = json.getJSONArray("actions").getJSONObject(0).getString("name");
        final var messageTimeStamp = json.getString("message_ts");
        System.out.println(json.toString());
        // Show/Hide defects button
        if ("hide_defects".equals(actionName)) {
            hideTestCases(messageTimeStamp);
        }
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

    private SlackResponse sendSlackMessage(final String token, final String channel, final List<Attachment> attachments)
            throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.postMessage")
                .queryParam("token", config.getToken())
                .queryParam("channel", config.getChannel())
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }

    private SlackResponse sendSlackMessage(final List<Attachment> attachments) throws JsonProcessingException {
        return sendSlackMessage(config.getToken(), config.getChannel(), attachments);
    }

    private SlackResponse updateSlackMessage(final List<Attachment> attachments, final String ts)
            throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.update")
                .queryParam("token", config.getToken())
                .queryParam("channel", config.getChannel())
                .queryParam("ts", ts)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
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
        final var slackResponse = sendSlackMessage(attachments);

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

        // Send
        updateSlackMessage(attachmentList, messageTimeStamp);
    }

    private void hideTestCases(final String messageTimeStamp) throws NotFoundException, IOException {
        // Remove test cases
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var attachmentList = getAttachmentsFromMessage(message);

        for (var attachment : attachmentList) {
            if (attachment.getFallback().equals("defect")) {
                attachmentList.remove(attachment);
            }
        }

        // Send
        updateSlackMessage(attachmentList, messageTimeStamp);
    }

    private void sendStackTrace(final int callbackId, final String channelId) throws NotFoundException,
            JsonProcessingException {
        final var testCase = testCaseRepository.findById(callbackId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var attachment = new AttachmentFactory().getStackTrace(testCase.getException());

        sendSlackMessage(config.getToken(), channelId, List.of(attachment));
    }

    private int getTestRunIdFromMessage(final MessageEntity messageEntity) throws JsonProcessingException {
        final var messageJson = new JSONObject(objectToString(messageEntity)).getJSONObject("message");

        return Integer.parseInt(messageJson
                .getJSONArray("attachments")
                .getJSONObject(0)
                .getString("fallback")
                .split("=")[1]);
    }

    private List<Attachment> getAttachmentsFromMessage(final MessageEntity messageEntity)
            throws IOException {
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
