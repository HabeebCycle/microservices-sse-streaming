package com.habeebcycle.microservice.serviceb.service;

import com.habeebcycle.sse.util.generator.MessageGenerator;
import com.habeebcycle.sse.util.payload.MessagePayload;
import com.habeebcycle.sse.util.server.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private final String serviceCUrl;
    private final ServerAddress serverAddress;
    private final WebClient webClient;

    public MessageService(WebClient.Builder webClient, ServerAddress serverAddress,
                          @Value("${service.service-c.host}") String serviceCHost,
                          @Value("${service.service-c.port}") String serviceCPort) {
        this.serverAddress = serverAddress;
        this.webClient = webClient.build();
        this.serviceCUrl = "http://" + serviceCHost + ":" + serviceCPort;
    }

    public Flux<MessagePayload> findAllMessages() {
        return Flux.interval(Duration.ofSeconds(3))
                .onBackpressureDrop()
                .map(this::generateAllMessages)
                .flatMapIterable(x -> x);
    }

    public MessagePayload getOneMessage() {
        return generatePayload();
    }

    public Mono<Void> getServiceCToProduce(String messageId) {
        String producerEndpoint = "/produce/" + messageId;
        return webClient
                .get()
                .uri(serviceCUrl + producerEndpoint)
                .retrieve()
                .bodyToMono(Void.class);
    }

    private List<MessagePayload> generateAllMessages(long interval) {
        LOG.info("Sending automated message in the next {} seconds", interval);
        return Collections.singletonList(generatePayload());

    }

    private MessagePayload generatePayload() {
        MessagePayload messagePayload = new MessagePayload(
                MessageGenerator.randomAuthor(),
                MessageGenerator.randomMessage(),
                MessageGenerator.getCurrentTimeStamp());
        messagePayload.setServiceAddress(serverAddress.getHostAddress());

        return messagePayload;
    }
}
