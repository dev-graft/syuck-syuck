package org.devgraft.auth.exception;

import org.devgraft.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotAllowUrlPatternException extends AbstractRequestException {
    public NotAllowUrlPatternException() {
        super("허용된 url 접근이 아닙니다.", HttpStatus.UNAUTHORIZED);
    }
}
