package org.devgraft.support.redis;

import org.devgraft.support.provider.SpyLocalDateTimeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class RedisStoreServiceImplTest {
    RedisStoreServiceImpl redisStoreService;
    SpyRedisTemplate spyRedisTemplate;
    SpyRedisDataKeyProvider spyRedisDataKeyProvider;
    SpyLocalDateTimeProvider spyLocalDateTimeProvider;
    SpyValueOperations spyValueOperations;
    @BeforeEach
    void setUp() {
        spyValueOperations = new SpyValueOperations();
        spyRedisTemplate = new SpyRedisTemplate(spyValueOperations);
        spyRedisDataKeyProvider = new SpyRedisDataKeyProvider();
        spyLocalDateTimeProvider = new SpyLocalDateTimeProvider();
        redisStoreService = new RedisStoreServiceImpl(spyRedisTemplate, spyRedisDataKeyProvider, spyLocalDateTimeProvider);
    }

    @DisplayName("데이터 추가/결과")
    @Test
    void addData_returnValue() {
        String givenData = "data";
        long givenTimeout = 0;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        RedisStoreAddDataResponse<String> response = redisStoreService.addData(givenData, givenTimeout, givenTimeUnit);

        assertThat(response.getCode()).isEqualTo("code");
        assertThat(response.getCreateAt()).isEqualTo(spyLocalDateTimeProvider.now());
        assertThat(response.getUpdateAt()).isEqualTo(spyLocalDateTimeProvider.now());
        assertThat(response.getValiditySeconds()).isEqualTo(givenTimeUnit.toSeconds(givenTimeout));
        assertThat(response.getTimeoutAt()).isEqualTo(response.getCreateAt().plusSeconds(givenTimeUnit.toSeconds(givenTimeout)));
        assertThat(response.getData()).isEqualTo(givenData);
    }

    @DisplayName("데이터 추가/패스1")
    @Test
    void addData_passesRedisDataOpsValue() {
        String givenData = "data";
        long givenTimeout = 0;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        redisStoreService.addData(givenData, givenTimeout, givenTimeUnit);

        assertThat(spyValueOperations.set_key_argument).isEqualTo(spyRedisDataKeyProvider.generate());
        assertThat(spyValueOperations.set_timeout_argument).isEqualTo(givenTimeout);
        assertThat(spyValueOperations.set_timeUnit_argument).isEqualTo(givenTimeUnit);
        assertThat(spyValueOperations.set_value_argument.getData()).isEqualTo(givenData);
        assertThat(spyValueOperations.set_value_argument.getSearchCode()).isEqualTo(spyRedisDataKeyProvider.generate());
        assertThat(spyValueOperations.set_value_argument.getCreatedAt()).isEqualTo(spyLocalDateTimeProvider.now());
        assertThat(spyValueOperations.set_value_argument.getUpdateAt()).isEqualTo(spyLocalDateTimeProvider.now());
        assertThat(spyValueOperations.set_value_argument.getValiditySeconds()).isEqualTo(givenTimeUnit.toSeconds(givenTimeout));
    }
}