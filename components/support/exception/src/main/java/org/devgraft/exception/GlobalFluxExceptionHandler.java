package org.devgraft.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@RestControllerAdvice
public class GlobalFluxExceptionHandler {
    @ExceptionHandler(AbstractRequestException.class)
    public RequestExceptionResponse handleRequestExceptionFlux(AbstractRequestException e, ServerHttpRequest request) {
        log.info("requestId: {} remoteAddr: {} path: {}", request.getId(), request.getRemoteAddress(), request.getPath());
        return new RequestExceptionResponse(e.getHttpStatus(), e.getMessage(), request.getPath().toString(), request.getId());
    }
}

