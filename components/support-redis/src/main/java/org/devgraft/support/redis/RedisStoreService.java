package org.devgraft.support.redis;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public interface RedisStoreService {
    <T extends Serializable> RedisStoreAddDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit);
    void removeData(String redisDataId);
    boolean setExpire(String dataSignKey, long timeout, TimeUnit timeUnit);
}
