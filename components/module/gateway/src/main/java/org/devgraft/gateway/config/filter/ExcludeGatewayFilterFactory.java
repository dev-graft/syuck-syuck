package org.devgraft.gateway.config.filter;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class ExcludeGatewayFilterFactory extends AbstractGatewayFilterFactory<ExcludeGatewayFilterFactory.Config> {

    public ExcludeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        ArrayList<PathPattern> excludePatterns = new ArrayList<>();

        if (config.excludeUrls != null) {
            config.excludeUrls.forEach(s -> excludePatterns.add(new PathPatternParser().parse(s)));
        }
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            Object address = request.getHeaders().getFirst("X-Forwarded-For") == null ?
                    request.getRemoteAddress() : request.getHeaders().getFirst("X-Forwarded-For");
            if (excludePatterns.stream().anyMatch(pathPattern -> pathPattern.matches(request.getPath().pathWithinApplication()))) {
                log.error("{} : {} {}", address, request.getMethod(), request.getURI());
                return onError(exchange.getResponse(), "허용되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED);
            }
//            if (config.excludeUrls != null) {
//                for (String pattern : config.excludeUrls) {
//                    if(path.matches(pattern)) {
//                        log.error("{} : {} {}", address, request.getMethod(), request.getURI());
//                        return onError(exchange.getResponse(), "허용되지 않은 접근입니다.", HttpStatus.UNAUTHORIZED);
//                    }
//                }
//            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerHttpResponse response, String message, HttpStatus status) {
        response.setStatusCode(status);
        DataBuffer buffer = response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

    @Setter
    public static class Config {
        private List<String> excludeUrls;
    }
}
