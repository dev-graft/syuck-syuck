package org.devgraft.member.exception;

import org.devgraft.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class ExistMemberException extends AbstractRequestException {
    public ExistMemberException() {
        super("중복된 아이디가 이미 존재합니다.", HttpStatus.BAD_REQUEST);
    }
}
