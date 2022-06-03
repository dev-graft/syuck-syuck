package org.devgraft.support.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisStoreServiceImpl implements RedisStoreService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisDataKeyProvider redisDataKeyProvider;

    @Override
    public <T extends Serializable> RedisStoreAddDataResponse<T> addData(T data) {
        String generate = redisDataKeyProvider.generate();



        return new RedisStoreAddDataResponse<T>(generate, data, 0, null, null, null, 0L);
    }

    @Override
    public <T extends Serializable> RedisStoreAddDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public void removeData(RedisDataId redisDataId) {

    }

    @Override
    public boolean setExpire(String dataSignKey, long timeout, TimeUnit timeUnit) {
        return false;
    }
}
