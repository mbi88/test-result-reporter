package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi.SlackConfig;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.AttachmentFactory;
import com.mbi.api.models.response.SlackResponse;
import com.mbi.api.repositories.SlackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Slack service.
 */
@Service
public class SlackService {

    @Autowired
    private SlackConfig config;

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private SlackRepository slackRepository;

    private SlackResponse sendSlackMessage(final String token, final String channel,
                                           final List<Attachment> attachments) throws JsonProcessingException {
        final var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final var attachmentsAsString = writer.writeValueAsString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.postMessage")
                .queryParam("token", token)
                .queryParam("channel", channel)
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

        final var messageEntity = new ModelMapper().map(slackResponse, MessageEntity.class);
        slackRepository.save(messageEntity);

        return messageEntity;
    }
}
