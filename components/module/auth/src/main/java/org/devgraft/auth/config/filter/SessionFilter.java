package org.devgraft.auth.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.devgraft.auth.exception.NotAllowUrlPatternException;
import org.devgraft.auth.exception.NotFoundCryptSessionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SessionFilter implements WebFilter {
    private final PathPattern basePattern;
    private final List<PathPattern> allowPatterns;

    public SessionFilter(@Value("${filter.allow-list:/auth/sha-test/**}") List<String> allows) {
        basePattern = new PathPatternParser().parse("/auth/**");
        allowPatterns = new ArrayList<>();
        if (allows != null) {
            allows.forEach(s -> allowPatterns.add(new PathPatternParser().parse(s)));
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Object address = request.getHeaders().getFirst("X-Forwarded-For") == null ?
                request.getRemoteAddress() : request.getHeaders().getFirst("X-Forwarded-For");
        log.info("{} : {} {}", address, request.getMethod(), request.getURI());

        if (allowPatterns.stream().anyMatch(pathPattern -> pathPattern.matches(request.getPath().pathWithinApplication()))) {
            return chain.filter(exchange);
        }
        if (basePattern.matches(request.getPath().pathWithinApplication())) {
            return exchange.getSession()
                    .doOnNext(webSession -> Optional.ofNullable(webSession.getAttribute("crypt"))
                            .orElseThrow(NotFoundCryptSessionException::new)
                    ).then(chain.filter(exchange));
        }
        throw new NotAllowUrlPatternException();
    }
}
