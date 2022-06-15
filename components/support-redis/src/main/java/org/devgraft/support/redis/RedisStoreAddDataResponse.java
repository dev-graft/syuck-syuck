package org.devgraft.support.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RedisStoreAddDataResponse<T extends Serializable> {
    private String code;
    private T data;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime timeoutAt;
    private long validitySeconds;

    public static <T extends Serializable> RedisStoreAddDataResponse<T> convert(RedisData<T> data) {
        return new RedisStoreAddDataResponse<>(
                data.getSearchCode(),
                data.getData(),
                data.getCreatedAt(),
                data.getUpdateAt(),
                data.getCreatedAt().plusSeconds(data.getValiditySeconds()),
                data.getValiditySeconds());
    }
}
