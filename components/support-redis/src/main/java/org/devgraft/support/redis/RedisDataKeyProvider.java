package org.devgraft.support.redis;

import java.util.UUID;

public class RedisDataKeyProvider {
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
