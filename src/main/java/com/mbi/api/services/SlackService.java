package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi.SlackConfig;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.AttachmentFactory;
import com.mbi.api.models.response.SlackResponse;
import com.mbi.api.repositories.SlackRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    private SlackResponse sendSlackMessage(final String token, final String channel,
                                           final List<Attachment> attachments) throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.postMessage")
                .queryParam("token", token)
                .queryParam("channel", channel)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }

    private SlackResponse updateSlackMessage(final String token, final String channel,
                                             final List<Attachment> attachments, final String ts)
            throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.update")
                .queryParam("token", token)
                .queryParam("channel", channel)
                .queryParam("ts", ts)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }

    public MessageEntity createSlackMessage(final int testRunId) throws NotFoundException, JsonProcessingException,
            BadRequestException {
        final var testRun = testRunService.getTestRunById(testRunId);
        final var testRunDiff = testRunService.getBuildDifference(testRunId);

        final List<Attachment> attachments = new ArrayList<>();
        final var mainAttachment = new AttachmentFactory().getMain(testRun, testRunDiff);
        attachments.add(mainAttachment);
        if (!testRun.isSuccessful()) {
            final var defectsAttachment = new AttachmentFactory().getAction();
            attachments.add(defectsAttachment);
        }
        final var slackResponse = sendSlackMessage(config.getToken(), config.getChannel(), attachments);

        if (!slackResponse.isOk()) {
            throw new BadRequestException(MessageEntity.class, slackResponse.getError());
        }

        final var messageEntity = mapper.map(slackResponse, MessageEntity.class);
        slackRepository.save(messageEntity);

        return messageEntity;
    }

    public void update(final String payload) throws NotFoundException, JsonProcessingException {
        final var json = new JSONObject(payload);
        final var actionValue = json.getJSONArray("actions").getJSONObject(0).getString("value");

        // Show/Hide defects button
        if ("defects".equals(actionValue)) {
            resend(json.getString("message_ts"));
        }

        // Show stacktrace button
        if ("stacktrace".equals(actionValue)) {
            resend(json.getString("aaaa"));
        }
    }

    private void resend(final String messageTimeStamp) throws NotFoundException, JsonProcessingException {
        // Get test run id
        final var message = slackRepository.findByTs(messageTimeStamp)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(MessageEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var testRunId = getTestRunIdFromMessage(message);

        // Get test cases
        final var testCases = testCaseService.getMethodsByStatus(testRunId, MethodStatus.FAILED, PageRequest.of(0, 10));

        // Add test cases
        final var attachmentList = getAttachmentsFromMessage(message);
        final var attachmentFactory = new AttachmentFactory();
        for (var testCase : testCases.getContent()) {
            final var attachment = attachmentFactory.getDefect(testCase);
            attachmentList.add(attachment);
        }
        System.out.println(attachmentList.toString());
        // Send
        updateSlackMessage(config.getToken(), config.getChannel(), attachmentList, messageTimeStamp);
    }

    private int getTestRunIdFromMessage(final MessageEntity messageEntity) throws JsonProcessingException {
        final var messageJson = new JSONObject(objectToString(messageEntity)).getJSONObject("message");

        System.out.println("!!!!!");
        System.out.println(messageJson.toString());
        return Integer.parseInt(messageJson
                .getJSONArray("attachments")
                .getJSONObject(0)
                .getString("fallback")
                .split("=")[1]);
    }

    private List<Attachment> getAttachmentsFromMessage(final MessageEntity messageEntity)
            throws JsonProcessingException {
        final List<Attachment> list = new ArrayList<>();
        final var attachments = new JSONObject(objectToString(messageEntity))
                .getJSONObject("message")
                .getJSONArray("attachments");

        for (var attach : attachments) {
            list.add(mapper.map(attach, Attachment.class));
        }

        return list;
    }

    private String objectToString(final Object object) throws JsonProcessingException {
        final var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }
}
