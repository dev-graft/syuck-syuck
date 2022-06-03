package org.devgraft.support.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RedisStoreServiceImplTest {
    RedisStoreServiceImpl redisStoreService;
    SpyRedisTemplate spyRedisTemplate;
    SpyRedisDataKeyProvider spyRedisDataKeyProvider;
    @BeforeEach
    void setUp() {
        spyRedisTemplate = new SpyRedisTemplate();
        spyRedisDataKeyProvider = new SpyRedisDataKeyProvider();
        redisStoreService = new RedisStoreServiceImpl(spyRedisTemplate, spyRedisDataKeyProvider);
    }

    @DisplayName("데이터 추가/결과")
    @Test
    void addData_returnValue() {
        String givenData = "data";

        RedisStoreAddDataResponse<String> response = redisStoreService.addData(givenData);

        assertThat(response.getCode()).isEqualTo("code");
        assertThat(response.getData()).isEqualTo(givenData);
        assertThat(response.getSearchCount()).isEqualTo(0);
    }
}