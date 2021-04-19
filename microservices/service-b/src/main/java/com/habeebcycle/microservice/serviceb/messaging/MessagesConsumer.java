package com.habeebcycle.microservice.serviceb.messaging;

import com.habeebcycle.sse.util.exceptions.EventProcessingException;
import com.habeebcycle.sse.util.payload.MessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class MessagesConsumer implements Consumer<FluxSink<MessagePayload>> {

    private final Logger LOG = LoggerFactory.getLogger(MessagesConsumer.class);

    private final BlockingQueue<MessagePayload> messageQueue = new LinkedBlockingQueue<>();

    @Bean
    public Consumer<MessagePayload> messageConsumer() {
        return messagePayload -> {
            LOG.info("Consuming message from the Queue {}", messagePayload);
            this.messageQueue.offer(messagePayload);
        };
    }

    @Override
    public void accept(FluxSink<MessagePayload> messagePayloadFluxSink) {
        try {
            MessagePayload messagePayload = messageQueue.take();
            messagePayloadFluxSink.next(messagePayload);
            LOG.info("Sending message back to the caller of message {}", messagePayload.getId());
        } catch (InterruptedException exception) {
            ReflectionUtils.rethrowRuntimeException(exception);
            throw new EventProcessingException(exception);
        }
    }
}
