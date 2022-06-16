package org.devgraft.support.redis.exception;

import support.exception.AbstractRequestException;

public class RedisDataNotFoundException extends AbstractRequestException {
    public RedisDataNotFoundException() {
        super("요청 정보가 존재하지 않습니다.");
    }
}
