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
        assertThat(response.getTimeoutAt()).isEqualTo(response.getCreateAt().plusSeconds(givenTimeUnit.toSeconds(givenTimeout)));
        assertThat(response.getData().getSearchCode()).isEqualTo(spyRedisDataKeyProvider.generate());
        assertThat(response.getData().getData()).isEqualTo(givenData);
        assertThat(response.getData().getCreatedAt()).isEqualTo(spyLocalDateTimeProvider.now());
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
    }

    @DisplayName("데이터 삭제/결과")
    @Test
    void removeData_returnValue() {
        String givenSearchCode = "searchCode";

        Boolean response = redisStoreService.removeData(givenSearchCode);

        assertThat(response).isFalse();
    }

    @DisplayName("데이터 삭제/패스1")
    @Test
    void removeData_passesSearchCode() {
        String givenSearchCode = "searchCode";

        redisStoreService.removeData(givenSearchCode);

        assertThat(spyRedisTemplate.delete_key_argument).isEqualTo(givenSearchCode);
    }

    @DisplayName("만료 시간 재설정/결과")
    @Test
    void setExpire_returnValue() {
        String givenSearchCode = "searchCode";
        long givenTimeout = 0L;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        Boolean response = redisStoreService.setExpire(givenSearchCode, givenTimeout, givenTimeUnit);

        assertThat(response).isFalse();
    }

    @DisplayName("만료 시간 재설정/패스1")
    @Test
    void setExpire_passesArgument() {
        String givenSearchCode = "searchCode";
        long givenTimeout = 0L;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        redisStoreService.setExpire(givenSearchCode, givenTimeout, givenTimeUnit);

        assertThat(spyRedisTemplate.expire_key_argument).isEqualTo(givenSearchCode);
        assertThat(spyRedisTemplate.expire_timeout_argument).isEqualTo(givenTimeout);
        assertThat(spyRedisTemplate.expire_timeUnit_argument).isEqualTo(givenTimeUnit);
    }
}