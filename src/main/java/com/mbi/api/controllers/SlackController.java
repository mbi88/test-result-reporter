package com.mbi.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.message.MessageEntity;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.slack.SlackRequestModel;
import com.mbi.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Slack controller.
 */
@RestController
public class SlackController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = POST, path = "/slack/messages", produces = "application/json")
    public ResponseEntity<MessageEntity> sendMessage(@Valid @RequestBody final SlackRequestModel slackRequestModel)
            throws JsonProcessingException, NotFoundException, BadRequestException {
        return new ResponseEntity<>(messageService.createSlackMessage(slackRequestModel.getTestRunId()), HttpStatus.OK);
    }

    @RequestMapping(method = POST, path = "/slack/interact")
    public ResponseEntity interact(@RequestParam("payload") final String payload) throws NotFoundException,
            IOException {
        messageService.interactWithSlack(payload);
        return new ResponseEntity(HttpStatus.OK);
    }
}
