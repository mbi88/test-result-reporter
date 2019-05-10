package com.mbi.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mbi.api.entities.slack.MessageEntity;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MessageEntity> sendMessage(@RequestParam("testRunId") final int testRunId)
            throws JsonProcessingException, NotFoundException, BadRequestException {
        return new ResponseEntity<>(messageService.createSlackMessage(testRunId), HttpStatus.OK);
    }

    @RequestMapping(method = POST, path = "/slack/interact")
    public ResponseEntity interact(@RequestParam("payload") final String payload) throws NotFoundException,
            IOException {
        messageService.interactWithSlack(payload);
        return new ResponseEntity(HttpStatus.OK);
    }
}
