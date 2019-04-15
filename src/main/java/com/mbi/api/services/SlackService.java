package com.mbi.api.services;

import com.mbi.api.models.request.slack.Attachment;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.repositories.SlackRepository;
import org.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Slack service.
 */
@Service
public class SlackService {

    @Autowired
    private SlackRepository slackRepository;

    @SuppressWarnings("all")
    public ResponseEntity<CreatedResponse> createSlackMessage(final String token,
                                                              final String channel,
                                                              final List<Attachment> attachments) {
        var json = new ModelMapper().map(attachments, JSONArray.class);
        System.out.println(json.toString(2));
        return (ResponseEntity<CreatedResponse>) slackRepository.findAll();
    }
}
