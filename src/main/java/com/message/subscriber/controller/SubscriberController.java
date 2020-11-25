package com.message.subscriber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.subscriber.jsonpojo.Message;
import com.message.subscriber.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriber")
public class SubscriberController {

    private final SubscriberService service;

    @Autowired
    public SubscriberController(SubscriberService service) {
        this.service = service;
    }

    @PostMapping("/message")
    public void saveMessage(@RequestBody String json) {

        ObjectMapper mapper = new ObjectMapper();

        Message message = null;
        try {
            message = mapper.readValue(json, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (message != null) {
            service.receiveMessage(message);
        }
    }
}
