package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi.SlackConfig;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.mappers.AttachmentMapper;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.response.SlackResponse;
import com.mbi.api.repositories.SlackRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

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

    public SlackResponse sendSlackMessage(final String token, final String channel,
                                          final List<Attachment> attachments)
            throws JsonProcessingException {
        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var attachmentsAsString = writer.writeValueAsString(attachments);

        var restTemplate = new RestTemplate();
        var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.postMessage")
                .queryParam("token", token)
                .queryParam("channel", channel)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }

    public ResponseEntity<MessageEntity> createSlackMessage(final int testRunId)
            throws NotFoundException, JsonProcessingException, BadRequestException {
        var testRun = testRunService.getTestRunById(testRunId).getBody();
        var testRunDiff = testRunService.getBuildDifference(testRunId).getBody();

        var attachment = new AttachmentMapper()
                .map(Objects.requireNonNull(testRun), Objects.requireNonNull(testRunDiff));
        var slackResponse = sendSlackMessage(config.getToken(), config.getChannel(), List.of(attachment));

        if (!slackResponse.getOk()) {
            throw new BadRequestException(MessageEntity.class, slackResponse.getError());
        }

        var messageEntity = new ModelMapper().map(slackResponse, MessageEntity.class);
        slackRepository.save(messageEntity);

        return new ResponseEntity<>(messageEntity, HttpStatus.OK);
    }
}
