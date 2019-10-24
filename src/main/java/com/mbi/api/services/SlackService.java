package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.SlackConfig;
import com.mbi.api.models.request.slack.Block;
import com.mbi.api.models.response.SlackResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Slack service.
 */
@Service
public class SlackService extends BaseService {

    @Autowired
    private SlackConfig config;

    public SlackResponse sendSlackMessage(final String token, final String channel, final List<Block> blocks)
            throws JsonProcessingException {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final var formData = new LinkedMultiValueMap<String, String>();
        formData.add("channel", channel);
        formData.add("token", token);
        formData.add("blocks", objectToString(blocks));

        final var request = new HttpEntity<>(formData, headers);
        final var restTemplate = new RestTemplate();

        return restTemplate
                .postForEntity(config.getUrl() + "chat.postMessage", request, SlackResponse.class)
                .getBody();
    }

    public SlackResponse sendSlackMessage(final List<Block> blocks) throws JsonProcessingException {
        return sendSlackMessage(config.getToken(), config.getChannel(), blocks);
    }

    public SlackResponse sendSlackMessage(final String channel, final List<Block> blocks)
            throws JsonProcessingException {
        return sendSlackMessage(config.getToken(), channel, blocks);
    }

    public SlackResponse updateSlackMessage(final List<Block> blocks, final String ts) throws JsonProcessingException {
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final var formData = new LinkedMultiValueMap<String, String>();
        formData.add("channel", config.getChannel());
        formData.add("token", config.getToken());
        formData.add("ts", ts);
        formData.add("blocks", objectToString(blocks));

        final var request = new HttpEntity<>(formData, headers);
        final var restTemplate = new RestTemplate();

        return restTemplate.postForEntity(config.getUrl() + "chat.update", request, SlackResponse.class).getBody();
    }
}
