package org.devgraft.gateway.config.exception;

import org.devgraft.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class UnAuthorizationException extends AbstractRequestException {
    public UnAuthorizationException() {
        super("인가 정보 확인이 실패하였습니다.", HttpStatus.UNAUTHORIZED);
    }
}
