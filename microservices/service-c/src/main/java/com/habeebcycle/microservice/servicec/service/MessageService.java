package com.habeebcycle.microservice.servicec.service;

import com.habeebcycle.sse.util.generator.MessageGenerator;
import com.habeebcycle.sse.util.payload.MessagePayload;
import com.habeebcycle.sse.util.server.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MessageService {
    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private static final String PRODUCER_BINDING_NAME = "messageProducer-out-0";

    private final StreamBridge streamBridge;
    private final ServerAddress serverAddress;

    public MessageService(StreamBridge streamBridge, ServerAddress serverAddress) {
        this.streamBridge = streamBridge;
        this.serverAddress = serverAddress;
    }

    public Mono<Void> sendMessagePayload(String messageId) {

        MessagePayload messagePayload = new MessagePayload(
                MessageGenerator.randomAuthor(),
                MessageGenerator.randomMessage(),
                MessageGenerator.getCurrentTimeStamp());
        messagePayload.setId(messageId);
        messagePayload.setServiceAddress(serverAddress.getHostAddress());

        LOG.info("Producing {} by service-c to Queue with Id: {} with delay of 5 seconds", messagePayload, messageId);

        return Mono.delay(Duration.ofSeconds(5))
                .map(aLong -> streamBridge.send(PRODUCER_BINDING_NAME, messagePayload))
                .then();
    }
}
