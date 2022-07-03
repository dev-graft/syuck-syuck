package org.devgraft.auth.exception;

import org.springframework.http.HttpStatus;
import support.exception.AbstractRequestException;

public class UnverifiedAuthRequestException extends AbstractRequestException {
    public UnverifiedAuthRequestException() {
        super("검증되지 않은 인증요청입니다.", HttpStatus.UNAUTHORIZED);
    }
}
