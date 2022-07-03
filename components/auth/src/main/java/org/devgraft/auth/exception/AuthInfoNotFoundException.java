package org.devgraft.auth.exception;

import support.exception.AbstractRequestException;

public class AuthInfoNotFoundException extends AbstractRequestException {
    public AuthInfoNotFoundException() {
        super("인증정보가 존재하지 않습니다.");
    }
}
