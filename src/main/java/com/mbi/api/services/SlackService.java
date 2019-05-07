package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.SlackConfig;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.Block;
import com.mbi.api.models.response.SlackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Slack service.
 */
@Service
@SuppressWarnings({"MultipleStringLiterals", "PMD.SystemPrintln", "PMD.AvoidDuplicateLiterals"})
public class SlackService extends BaseService {

    @Autowired
    private SlackConfig config;

    public SlackResponse sendSlackMessage(final String token, final String channel, final List<Attachment> attachments)
            throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);

        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.postMessage")
                .queryParam("token", token)
                .queryParam("channel", channel)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }

    public SlackResponse sendSlackMessage2(final String token, final String channel, final List<Block> blocks) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("channel", channel);
        map.add("token", token);
        map.add("blocks", blocks);

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, headers);

        final var restTemplate = new RestTemplate();
        return restTemplate.postForEntity(config.getUrl() + "chat.postMessage",
                request, SlackResponse.class).getBody();
    }

    public SlackResponse sendSlackMessage(final List<Attachment> attachments) throws JsonProcessingException {
        return sendSlackMessage(config.getToken(), config.getChannel(), attachments);
    }

    public SlackResponse sendSlackMessage2(final List<Block> blocks) {
        return sendSlackMessage2(config.getToken(), config.getChannel(), blocks);
    }

    public SlackResponse sendSlackMessage(final String channel, final List<Attachment> attachments)
            throws JsonProcessingException {
        return sendSlackMessage(config.getToken(), channel, attachments);
    }

    public SlackResponse updateSlackMessage(final List<Attachment> attachments, final String ts)
            throws JsonProcessingException {
        final var attachmentsAsString = objectToString(attachments);
        System.out.println(attachmentsAsString);
        final var restTemplate = new RestTemplate();
        final var builder = UriComponentsBuilder.fromUriString(config.getUrl() + "chat.update")
                .queryParam("token", config.getToken())
                .queryParam("channel", config.getChannel())
                .queryParam("ts", ts)
                .queryParam("attachments", attachmentsAsString);

        return restTemplate.getForEntity(builder.build().toUri(), SlackResponse.class).getBody();
    }
}
