package org.devgraft.support.redis.service;

import lombok.RequiredArgsConstructor;
import org.devgraft.support.provider.LocalDateTimeProvider;
import org.devgraft.support.redis.RedisData;
import org.devgraft.support.redis.RedisDataKeyProvider;
import org.devgraft.support.redis.exception.RedisDataNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisStoreServiceImpl implements RedisStoreService {
    private final RedisTemplate<String, RedisData<? extends Serializable>> redisTemplate;
    private final RedisDataKeyProvider redisDataKeyProvider;
    private final LocalDateTimeProvider localDateTimeProvider;

    @Override
    public <T extends Serializable> RedisStoreAddDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit) {
        String generate = redisDataKeyProvider.generate();
        LocalDateTime now = localDateTimeProvider.now();
        RedisData<T> redisData = new RedisData<>(generate, data, now);
        redisTemplate.opsForValue().set(generate, redisData, timeout, timeUnit);
        return new RedisStoreAddDataResponse<>(generate, redisData, now, now.plusSeconds(timeUnit.toSeconds(timeout)));
    }

    @Override
    public <T extends Serializable> RedisStoreGetDataResponse<T> getData(String dataSignKey) throws RedisDataNotFoundException {
        ValueOperations<String, RedisData<?>> opsForValue = redisTemplate.opsForValue();
        RedisData<?> redisData = Optional.ofNullable(opsForValue.get(dataSignKey)).orElseThrow(RedisDataNotFoundException::new);
        Long expire = Optional.ofNullable(redisTemplate.getExpire(dataSignKey, TimeUnit.SECONDS)).orElseThrow(RedisDataNotFoundException::new);

        return new RedisStoreGetDataResponse<>(dataSignKey,
                (RedisData<T>) redisData,
                redisData.getCreatedAt(),
                0 <= expire ? localDateTimeProvider.now().plusSeconds(expire) : null);
    }

    @Override
    public Boolean removeData(String searchCode) {
        return redisTemplate.delete(searchCode);
    }

    @Override
    public Boolean setExpire(String dataSignKey, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(dataSignKey, timeout, timeUnit);
    }
}
