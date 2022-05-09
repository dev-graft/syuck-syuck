package org.devgraft.auth.exception;

import org.devgraft.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotFoundCryptSessionException extends AbstractRequestException {
    public NotFoundCryptSessionException() {
        super("Crypt를 보유한 세션이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED);
    }
}
