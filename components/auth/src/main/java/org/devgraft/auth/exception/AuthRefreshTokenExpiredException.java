package org.devgraft.auth.exception;

import org.springframework.http.HttpStatus;
import support.exception.AbstractRequestException;

public class AuthRefreshTokenExpiredException extends AbstractRequestException {
    public AuthRefreshTokenExpiredException() {
        super("인증 정보가 만료되었습니다. 로그인 요청을 진행해주세요.", HttpStatus.UNAUTHORIZED);
    }
}
