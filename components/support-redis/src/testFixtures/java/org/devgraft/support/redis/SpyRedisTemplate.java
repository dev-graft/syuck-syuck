package org.devgraft.support.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class SpyRedisTemplate extends RedisTemplate<String, RedisData<?>> {
    public SpyValueOperations valueOperations;

    public SpyRedisTemplate(SpyValueOperations valueOperations) {
        this.valueOperations = valueOperations;
    }

    @Override
    public ValueOperations<String, RedisData<?>> opsForValue() {
        return this.valueOperations;
    }
}
