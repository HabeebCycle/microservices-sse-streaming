package com.habeebcycle.microservice.serviceb.handler;

import com.habeebcycle.microservice.serviceb.service.MessageService;
import com.habeebcycle.sse.util.payload.MessagePayload;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import static org.springframework.web.reactive.function.BodyInserters.fromServerSentEvents;

@Component
public class MessageHandler {

    private final MessageService messageService;

    public MessageHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    public Mono<ServerResponse> handleRequest(final ServerRequest serverRequest) {
        MessagePayload message = messageService.getOneMessage();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(fromServerSentEvents(Flux.interval(Duration.ofSeconds(3))
                        .map(interval -> ServerSentEvent.<MessagePayload>builder()
                                .id("id")
                                .event("periodic-event")
                                .data(message)
                                .build())));
                //.body(fromPublisher(messages, MessagePayload.class));
    }
}
