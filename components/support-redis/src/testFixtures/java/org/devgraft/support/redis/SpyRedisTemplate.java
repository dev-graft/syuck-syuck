package org.devgraft.support.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

public class SpyRedisTemplate extends RedisTemplate<String, RedisData<?>> {
    public SpyValueOperations valueOperations;
    public String delete_key_argument;
    public Boolean delete_returnValue = false;
    public String expire_key_argument;
    public long expire_timeout_argument;
    public TimeUnit expire_timeUnit_argument;
    public Boolean expire_returnValue = false;

    public SpyRedisTemplate(SpyValueOperations valueOperations) {
        this.valueOperations = valueOperations;
    }

    @Override
    public ValueOperations<String, RedisData<?>> opsForValue() {
        return this.valueOperations;
    }

    @Override
    public Boolean delete(String key) {
        this.delete_key_argument = key;
        return delete_returnValue;
    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        this.expire_key_argument = key;
        this.expire_timeout_argument = timeout;
        this.expire_timeUnit_argument = unit;
        return this.expire_returnValue;
    }
}
