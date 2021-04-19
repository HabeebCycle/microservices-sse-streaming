package com.habeebcycle.microservice.serviceb.controller;

import com.habeebcycle.microservice.serviceb.messaging.MessagesConsumer;
import com.habeebcycle.microservice.serviceb.service.MessageService;
import com.habeebcycle.sse.util.payload.MessagePayload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/message/stream")
public class MessageController {

    private final MessageService messageService;
    private final Flux<MessagePayload> consumerEvents;

    public MessageController(MessageService messageService, MessagesConsumer messagesConsumer) {
        this.messageService = messageService;
        this.consumerEvents = Flux.create(messagesConsumer.sinkConsumer()).share();
    }

    @GetMapping(path = "/interval", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MessagePayload> getIntervalComment() {
        return messageService.findAllMessages();
    }

    @GetMapping(path = "/api/{messageId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<MessagePayload> getProcessedMessage(@PathVariable String messageId) {
        messageService
                .getServiceCToProduce(messageId)
                .doOnSuccess(b -> System.out.println(b ? "Called API waiting for the message to be consumed"
                        : "API Call failed")).subscribe();
        return consumerEvents.next();
    }
}
