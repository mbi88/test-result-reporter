package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.mappers.AttachmentMapper;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.request.slack.Field;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TestRunService testRunService;

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

    public ResponseEntity<Object> createSlackMessage(final int testRunId)
            throws NotFoundException, JsonProcessingException {
        var testRun = testRunService.getTestRunById(testRunId).getBody();
        var testRunDiff = testRunService.getBuildDifference(testRunId).getBody();

        var attachment = new AttachmentMapper().map(testRun, testRunDiff);
        var r = createSlackMessage("xoxb-442740729539-590709806882-yOpec3dZwklgCtM2h7sOntl0",
                "CD1S9CDNK",
                List.of(attachment));

        return new ResponseEntity<Object>(r.getBody().toString(), r.getStatusCode());
    }
}
