package org.devgraft.support.redis;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SpyValueOperations implements ValueOperations<String, RedisData<?>> {
    public String set_key_argument;
    public Object get_key_argument;
    public RedisData<?> set_value_argument;
    public long set_timeout_argument;
    public TimeUnit set_timeUnit_argument;

    @Override
    public void set(String key, RedisData<?> value) {
        this.set_key_argument = key;
        this.set_value_argument = value;
    }

    @Override
    public void set(String key, RedisData<?> value, long timeout, TimeUnit unit) {
        this.set_key_argument = key;
        this.set_value_argument = value;
        this.set_timeout_argument = timeout;
        this.set_timeUnit_argument = unit;
    }

    @Override
    public Boolean setIfAbsent(String key, RedisData<?> value) {
        return null;
    }

    @Override
    public Boolean setIfAbsent(String key, RedisData<?> value, long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public Boolean setIfPresent(String key, RedisData<?> value) {
        return null;
    }

    @Override
    public Boolean setIfPresent(String key, RedisData<?> value, long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public void multiSet(Map<? extends String, ? extends RedisData<?>> map) {

    }

    @Override
    public Boolean multiSetIfAbsent(Map<? extends String, ? extends RedisData<?>> map) {
        return null;
    }

    @Override
    public RedisData<?> get(Object key) {
        this.get_key_argument = key;
        return null;
    }

    @Override
    public RedisData<?> getAndDelete(String key) {
        return null;
    }

    @Override
    public RedisData<?> getAndExpire(String key, long timeout, TimeUnit unit) {
        return null;
    }

    @Override
    public RedisData<?> getAndExpire(String key, Duration timeout) {
        return null;
    }

    @Override
    public RedisData<?> getAndPersist(String key) {
        return null;
    }

    @Override
    public RedisData<?> getAndSet(String key, RedisData<?> value) {
        return null;
    }

    @Override
    public List<RedisData<?>> multiGet(Collection<String> keys) {
        return null;
    }

    @Override
    public Long increment(String key) {
        return null;
    }

    @Override
    public Long increment(String key, long delta) {
        return null;
    }

    @Override
    public Double increment(String key, double delta) {
        return null;
    }

    @Override
    public Long decrement(String key) {
        return null;
    }

    @Override
    public Long decrement(String key, long delta) {
        return null;
    }

    @Override
    public Integer append(String key, String value) {
        return null;
    }

    @Override
    public String get(String key, long start, long end) {
        return null;
    }

    @Override
    public void set(String key, RedisData<?> value, long offset) {

    }

    @Override
    public Long size(String key) {
        return null;
    }

    @Override
    public Boolean setBit(String key, long offset, boolean value) {
        return null;
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return null;
    }

    @Override
    public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
        return null;
    }

    @Override
    public RedisOperations<String, RedisData<?>> getOperations() {
        return null;
    }
}
