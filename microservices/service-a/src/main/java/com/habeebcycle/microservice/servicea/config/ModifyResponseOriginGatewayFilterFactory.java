package com.habeebcycle.microservice.servicea.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

//@Component
public class ModifyResponseOriginGatewayFilterFactory extends
        AbstractGatewayFilterFactory<ModifyResponseOriginGatewayFilterFactory.Config> {

    public ModifyResponseOriginGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            ServerHttpRequest request = exchange.getRequest();
            String origin = request.getHeaders().getOrigin();
            System.out.println("Origin is: " + origin);
            response.getHeaders().setAccessControlAllowOrigin(origin);
        })));
    }

    public static class Config {
    }
}
