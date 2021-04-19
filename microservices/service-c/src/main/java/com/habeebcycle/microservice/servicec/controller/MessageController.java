package com.habeebcycle.microservice.servicec.controller;

import com.habeebcycle.microservice.servicec.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/produce/{messageId}")
    public Mono<Void> produceMessage(@PathVariable String messageId) {
        LOG.info("Getting called through /produce... I am going to produce to Message Queue, Please Consume");
        return messageService.sendMessagePayload(messageId);
    }
}
