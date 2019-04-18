package com.mbi.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.services.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Slack controller.
 */
@RestController
public class SlackController {

    @Autowired
    private SlackService slackService;

    @RequestMapping(method = POST, path = "/slack/messages", produces = "application/json")
    public ResponseEntity<Object> sendMessage(
            @RequestParam("token") final String token,
            @RequestParam("channel") final String channel,
            @Valid @RequestBody final List<Attachment> attachments) throws JsonProcessingException {
        return slackService.createSlackMessage(token, channel, attachments);
    }

    @RequestMapping(method = POST, path = "/slack/messages2", produces = "application/json")
    public ResponseEntity<Object> sendMessage2(@RequestParam("testRunId") final int testRunId)
            throws JsonProcessingException, NotFoundException {
        return slackService.createSlackMessage(testRunId);
    }
}
