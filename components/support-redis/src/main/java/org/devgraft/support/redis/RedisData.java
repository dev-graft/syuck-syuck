package org.devgraft.support.redis;

import lombok.AccessLevel;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RedisData<T extends Serializable> implements Serializable {
    private String searchCode;
    private T data;
    private LocalDateTime createdAt;

    public RedisData(String searchCode, T data, LocalDateTime createdAt) {
        this.searchCode = searchCode;
        this.data = data;
        this.createdAt = createdAt;
    }

    public static class RedisDataBuilder<BT extends Serializable> {
        private String searchCode;
        private BT data;
        private LocalDateTime createAt;

        public RedisDataBuilder() {

        }

        public RedisDataBuilder<BT> setSearchCode(String searchCode) {
            this.searchCode = searchCode;
            return this;
        }

        public RedisDataBuilder<BT> setData(BT data) {
            this.data = data;
            return this;
        }

        public RedisDataBuilder<BT> setCreateAt(LocalDateTime createAt) {
            this.createAt = createAt;
            return this;
        }

        public RedisData<BT> build() {
            return new RedisData<>(this.searchCode, this.data, this.createAt);
        }
    }
}
