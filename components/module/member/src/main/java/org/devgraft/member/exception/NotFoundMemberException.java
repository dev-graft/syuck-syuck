package org.devgraft.member.exception;

import org.devgraft.exception.AbstractRequestException;
import org.springframework.http.HttpStatus;

public class NotFoundMemberException extends AbstractRequestException {
    public NotFoundMemberException() {
        super("요청한 사용자의 정보가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
    }
}
