package org.devgraft.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;


@Slf4j
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@RestControllerAdvice
public class GlobalMvcExceptionHandler {
    @ExceptionHandler(AbstractRequestException.class)
    public RequestExceptionResponse handleRequestExceptionMvc(AbstractRequestException e, ServletWebRequest request) {
        log.info("remoteAddr: {} path: {}", request.getRequest().getRemoteAddr(), request.getRequest().getServletPath());
        return new RequestExceptionResponse(e.getHttpStatus(), e.getMessage(), request.getRequest().getServletPath(), request.getSessionId());
    }
}
