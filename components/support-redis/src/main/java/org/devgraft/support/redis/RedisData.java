package org.devgraft.support.redis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * variables:
 *  searchCode: User to search the data.
 *  data: data
 *  createdAt: This is the creation data.
 *  updateAt: This is the update date.
 *  validitySeconds: Use to reset the expiration time on update.
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RedisData<T extends Serializable> implements Serializable {
    private String searchCode;
    private T data;
    private LocalDateTime createdAt;
}
