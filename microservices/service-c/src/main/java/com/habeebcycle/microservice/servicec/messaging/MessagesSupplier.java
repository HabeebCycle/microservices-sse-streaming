package com.habeebcycle.microservice.servicec.messaging;

import com.habeebcycle.sse.util.payload.MessagePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class MessagesSupplier {

    private final Logger LOG = LoggerFactory.getLogger(MessagesSupplier.class);

    @Bean
    public Supplier<MessagePayload> messageProducer() {
        return () -> null;
    }
}
