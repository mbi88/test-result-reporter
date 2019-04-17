package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi.api.models.request.slack.Attachment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Slack service.
 */
@Service
@SuppressWarnings("all")
public class SlackService {

    // @Autowired
    // private SlackRepository slackRepository;
    public ResponseEntity<Object> createSlackMessage(final String token, final String channel,
                                                     final List<Attachment> attachments)
            throws JsonProcessingException {
        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        var attachmentsAsString = writer.writeValueAsString(attachments);

        var restTemplate = new RestTemplate();
        var builder = UriComponentsBuilder.fromUriString("https://slack.com/api/chat.postMessage")
                .queryParam("token", token)
                .queryParam("channel", channel)
                .queryParam("attachments", attachmentsAsString);
        var r = restTemplate.getForEntity(builder.build().toUri(), Object.class);

        return new ResponseEntity<Object>(r.getBody().toString(), r.getStatusCode());
    }
}
