package org.devgraft.support.redis.service;

import org.devgraft.support.redis.exception.RedisDataNotFoundException;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public interface RedisStoreService {
    <T extends Serializable> RedisStoreAddDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit);
    <T extends Serializable> RedisStoreGetDataResponse<T> getData(String dataSignKey) throws RedisDataNotFoundException;
    Boolean removeData(String searchCode);
    Boolean setExpire(String dataSignKey, long timeout, TimeUnit timeUnit);
}
