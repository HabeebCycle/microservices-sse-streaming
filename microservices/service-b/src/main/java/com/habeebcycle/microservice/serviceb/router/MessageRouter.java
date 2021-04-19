package com.habeebcycle.microservice.serviceb.router;

import com.habeebcycle.microservice.serviceb.handler.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class MessageRouter {

    @Bean
    public RouterFunction<ServerResponse> messageRoutes(final MessageHandler messageHandler) {
        return RouterFunctions
                .route(GET("/message/stream/router").and(accept(TEXT_EVENT_STREAM)),
                        messageHandler::handleRequest);

    }
}
