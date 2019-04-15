package com.mbi.api.controllers;

import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.services.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CreatedResponse> sendMessage(
            @RequestParam("token") final String token,
            @RequestParam("channel") final String channel,
            @RequestParam("attachments") final List<Attachment> attachments) {
        return slackService.createSlackMessage(token, channel, attachments);
    }
}
