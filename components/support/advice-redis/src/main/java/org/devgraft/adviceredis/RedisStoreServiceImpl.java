package org.devgraft.adviceredis;

import org.devgraft.adviceredis.data.RedisData;
import org.devgraft.adviceredis.data.RedisStoreDataResponse;
import com.dreamsecurity.simple.provider.SHA256Provider;
import com.dreamsecurity.simple.provider.UUIDProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Base64;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisStoreServiceImpl implements RedisStoreService {
    private final SHA256Provider sha256Provider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UUIDProvider uuidProvider;

    @Override
    public <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey) {
        return this.getData(dataSignKey, -1, null);
    }

    @Override
    public <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey, long timeout, TimeUnit timeUnit) {
        String decodeSignKey = getDecodeSignKey(dataSignKey);
        RedisData<T> rData = (RedisData<T>)redisTemplate.boundValueOps(decodeSignKey).get();
        if (rData != null) {
            rData.searchUp();
            redisTemplate.boundValueOps(decodeSignKey).set(rData);
            if (-1 < timeout)
                redisTemplate.expire(decodeSignKey, timeout, timeUnit);
            return Optional.of(RedisStoreDataResponse.convert(rData));
        }
        return Optional.empty();
    }

    @Override
    public void updateData(String dataSignKey, Serializable data) {
        redisTemplate.boundValueOps(getDecodeSignKey(dataSignKey)).set(data);
    }

    @Override
    public void updateData(String dataSignKey, Serializable data, long timeout, TimeUnit timeUnit) {
        redisTemplate.boundValueOps(getDecodeSignKey(dataSignKey)).set(data, timeout, timeUnit);
    }

    @Override
    public void setExpire(String dataSignKey, long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(getDecodeSignKey(dataSignKey), timeout, timeUnit);
    }

    @Override
    public void removeData(String dataSignKey) {
        redisTemplate.delete(getDecodeSignKey(dataSignKey));
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data) {
        return addData(data, null);
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix) {
        return this.addData(data, prefix, -1, null);
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit) {
        return this.addData(data, null, timeout, timeUnit);
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix, long timeout, TimeUnit timeUnit) {
        String dataSignKey = generateSignKey(prefix);
        RedisData<T> redisData = RedisData.create(dataSignKey, data, timeUnit != null ? Math.max(-1, timeUnit.toSeconds(timeout)) : -1);
        ValueOperations<String, Object> opsValue = redisTemplate.opsForValue();
        opsValue.set(dataSignKey, redisData);
        if (-1 < redisData.getValiditySeconds())
            redisTemplate.expire(dataSignKey, timeout, timeUnit);
        return RedisStoreDataResponse.convert(redisData);
    }


    private String generateSignKey(String prefix) {
        String uuidStr = uuidProvider.randomUUID().toString();
        return sha256Provider.encrypt(uuidStr, StringUtils.hasText(prefix) ? prefix : "CODE-SECRET");
    }

    private String getDecodeSignKey(String dataSignKey) {
        return new String(Base64.getUrlDecoder().decode(dataSignKey));
    }
}
