package org.devgraft.support.redis;

public class StubRedisDataKeyProvider extends RedisDataKeyProvider {
    public String generate_returnValue = "code";
    @Override
    public String generate() {
        return generate_returnValue;
    }
}
