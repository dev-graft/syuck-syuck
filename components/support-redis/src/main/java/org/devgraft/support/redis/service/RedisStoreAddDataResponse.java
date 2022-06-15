package org.devgraft.support.redis.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.devgraft.support.redis.RedisData;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RedisStoreAddDataResponse<T extends Serializable> {
    private String code;
    private RedisData<T> data;
    private LocalDateTime createAt;
    private LocalDateTime timeoutAt;
}
