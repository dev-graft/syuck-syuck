package org.devgraft.authstore.service;

import org.devgraft.adviceredis.RedisStoreService;
import org.devgraft.adviceredis.data.RedisStoreDataResponse;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class SpyRedisStoreService implements RedisStoreService {
    public Serializable addData_data_argument;
    public String addData_prefix_argument;
    public String addData_returnValue = "dataSignKey";
    @Override
    public <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey) {
        return Optional.empty();
    }

    @Override
    public <T extends Serializable> Optional<RedisStoreDataResponse<T>> getData(String dataSignKey, long timeout, TimeUnit timeUnit) {
        return Optional.empty();
    }

    @Override
    public void updateData(String dataSignKey, Serializable data) {

    }

    @Override
    public void updateData(String dataSignKey, Serializable data, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void setExpire(String dataSignKey, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public void removeData(String dataSignKey) {

    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data) {
        this.addData_data_argument = data;
        return new RedisStoreDataResponse<>(addData_returnValue, data, 0, null, null, null, 0);
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix) {
        this.addData_data_argument = data;
        addData_prefix_argument = prefix;
        return new RedisStoreDataResponse<>(addData_returnValue, data, 0, null, null, null, 0);
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, long timeout, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public <T extends Serializable> RedisStoreDataResponse<T> addData(T data, String prefix, long timeout, TimeUnit timeUnit) {
        return null;
    }
}
