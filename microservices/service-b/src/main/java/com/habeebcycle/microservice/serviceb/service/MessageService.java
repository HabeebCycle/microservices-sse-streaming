package com.habeebcycle.microservice.serviceb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.habeebcycle.sse.util.exceptions.BadRequestException;
import com.habeebcycle.sse.util.exceptions.NotFoundException;
import com.habeebcycle.sse.util.generator.MessageGenerator;
import com.habeebcycle.sse.util.http.error.HttpErrorInfo;
import com.habeebcycle.sse.util.payload.MessagePayload;
import com.habeebcycle.sse.util.server.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private final String serviceCUrl;
    private final ServerAddress serverAddress;
    private final WebClient webClient;
    private final ObjectMapper mapper;

    public MessageService(WebClient.Builder webClient, ServerAddress serverAddress, ObjectMapper mapper,
                          @Value("${service.service-c.host}") String serviceCHost,
                          @Value("${service.service-c.port}") String serviceCPort) {
        this.serverAddress = serverAddress;
        this.webClient = webClient.build();
        this.mapper = mapper;
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

    public Mono<Boolean> getServiceCToProduce(String messageId) {
        String producerEndpoint = serviceCUrl + "/produce/" + messageId;
        LOG.info("Calling Service C on {} to produce message with messageId {}", producerEndpoint, messageId);
        return webClient
                .get()
                .uri(producerEndpoint)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorMap(WebClientException.class, this::handleHttpClientException);
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

    private Throwable handleHttpClientException(Throwable ex) {

        if (!(ex instanceof WebClientResponseException)) {
            LOG.warn("Got an unexpected error: {}, will rethrow it", ex.toString());
            return ex;
        }

        WebClientResponseException wcre = (WebClientResponseException)ex;

        switch (wcre.getStatusCode()) {
            case NOT_FOUND -> throw new NotFoundException(getErrorMessage(wcre));
            case BAD_REQUEST -> throw new BadRequestException(getErrorMessage(wcre));
            default -> {
                LOG.warn("Got a unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
                LOG.warn("Error body: {}", wcre.getResponseBodyAsString());
                return ex;
            }
        }
    }

    private String getErrorMessage(WebClientResponseException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioe) {
            return ex.getMessage();
        }
    }
}
