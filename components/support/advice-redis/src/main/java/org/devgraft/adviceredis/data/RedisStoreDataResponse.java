package org.devgraft.adviceredis.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RedisStoreDataResponse<T extends Serializable> {
    private String code;
    private T data;
    private int searchCount;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime timeoutAt;
    private long validitySeconds;

    public static <T extends Serializable> RedisStoreDataResponse<T> convert(RedisData<T> data) {
        return new RedisStoreDataResponse<>(
                data.getSearchCode(),
                data.getData(),
                data.getSearchCount(),
                data.getCreatedAt(),
                data.getUpdateAt(),
                data.getCreatedAt().plusSeconds(data.getValiditySeconds()),
                data.getValiditySeconds());
    }
}
