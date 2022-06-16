package org.devgraft.support.redis;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RedisDataFixture {
    public static <T extends Serializable>  RedisData.RedisDataBuilder<T> anRedisData(T data) {
        return new RedisData.RedisDataBuilder<T>()
                .setSearchCode("code")
                .setCreateAt(LocalDateTime.now())
                .setData(data);
    }
}
