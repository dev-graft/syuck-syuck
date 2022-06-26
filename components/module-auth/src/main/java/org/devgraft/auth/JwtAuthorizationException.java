package org.devgraft.auth;

import org.springframework.http.HttpStatus;
import support.exception.AbstractRequestException;

public class JwtAuthorizationException extends AbstractRequestException {
    public JwtAuthorizationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
