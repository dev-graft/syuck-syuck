package org.devgraft.gateway.config.filter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<LoggingGatewayFilterFactory.Config> {

    public LoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Object address = request.getHeaders().getFirst("X-Forwarded-For") == null ?
                    request.getRemoteAddress() : request.getHeaders().getFirst("X-Forwarded-For");

            log.info("{} : {} {}", address, request.getMethod(), request.getURI());
            return chain.filter(exchange);
        });
    }

    @Setter
    public static class Config {

    }
}
