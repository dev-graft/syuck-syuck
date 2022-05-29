package org.devgraft.adviceredis;

import org.devgraft.adviceredis.data.RedisStoreDataResponse;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface RedisStoreService {
    // 인증 정보 조회
    <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey);

    <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey, long timeout, TimeUnit timeUnit);

    // 인증 정보 변경
    void updateData(String dataSignKey, Serializable data);

    void updateData(String dataSignKey, Serializable data, long timeout, TimeUnit timeUnit);

    void setExpire(String dataSignKey, long timeout, TimeUnit timeUnit);

    void removeData(String dataSignKey);
    <T extends Serializable> RedisStoreDataResponse<T> addData(T data);
    <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix);
    <T extends Serializable> RedisStoreDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit);
    <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix, long timeout, TimeUnit timeUnit);
}
