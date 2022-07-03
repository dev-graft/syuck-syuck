package org.devgraft.auth.exception;

import org.springframework.http.HttpStatus;
import support.exception.AbstractRequestException;

public class AuthAccessTokenExpiredException extends AbstractRequestException {
    public AuthAccessTokenExpiredException() {
        super("인증 정보가 만료되었습니다. 갱신요청을 진행해주세요.", HttpStatus.UNAUTHORIZED);
    }
}
