package org.devgraft.support.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RedisData<T extends Serializable> implements Serializable {
    private String searchCode;
    private T data;
    private int searchCount;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private long validitySeconds;

    public static <T extends Serializable>RedisData<T> create(String dataSignKey, T data, long validitySeconds) {
        LocalDateTime now = LocalDateTime.now();
        return new RedisData<>(Base64.getUrlEncoder().encodeToString(dataSignKey.getBytes()), data, 0, now, now, validitySeconds);
    }

    public int searchUp() {
        this.updateAt = LocalDateTime.now();
        return ++this.searchCount;
    }
}
