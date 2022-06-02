package org.devgraft.support.redis;

import java.io.Serializable;

public interface RedisStoreService {
    <T extends Serializable> T addData(T data);
    void removeData(RedisDataId redisDataId);
}
