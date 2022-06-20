package org.devgraft.support.redis.service;

import org.devgraft.support.provider.StubLocalDateTimeProvider;
import org.devgraft.support.redis.RedisDataFixture;
import org.devgraft.support.redis.StubRedisDataKeyProvider;
import org.devgraft.support.redis.SpyRedisTemplate;
import org.devgraft.support.redis.SpyValueOperations;
import org.devgraft.support.redis.exception.RedisDataNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class RedisStoreServiceImplTest {
    RedisStoreServiceImpl redisStoreService;
    SpyRedisTemplate spyRedisTemplate;
    StubRedisDataKeyProvider stubRedisDataKeyProvider;
    StubLocalDateTimeProvider stubLocalDateTimeProvider;
    SpyValueOperations spyValueOperations;

    @BeforeEach
    void setUp() {
        spyValueOperations = new SpyValueOperations();
        spyRedisTemplate = new SpyRedisTemplate(spyValueOperations);
        stubRedisDataKeyProvider = new StubRedisDataKeyProvider();
        stubLocalDateTimeProvider = new StubLocalDateTimeProvider();
        redisStoreService = new RedisStoreServiceImpl(spyRedisTemplate, stubRedisDataKeyProvider, stubLocalDateTimeProvider);
    }

    @DisplayName("데이터 추가/결과")
    @Test
    void addData_returnValue() {
        String givenData = "data";
        long givenTimeout = 0;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        RedisStoreAddDataResponse<String> response = redisStoreService.addData(givenData, givenTimeout, givenTimeUnit);

        assertThat(response.getCode()).isEqualTo("code");
        assertThat(response.getCreateAt()).isEqualTo(stubLocalDateTimeProvider.now());
        assertThat(response.getTimeoutAt()).isEqualTo(response.getCreateAt().plusSeconds(givenTimeUnit.toSeconds(givenTimeout)));
        assertThat(response.getData().getSearchCode()).isEqualTo(stubRedisDataKeyProvider.generate());
        assertThat(response.getData().getData()).isEqualTo(givenData);
        assertThat(response.getData().getCreatedAt()).isEqualTo(stubLocalDateTimeProvider.now());
    }

    @DisplayName("데이터 추가/패스1")
    @Test
    void addData_passesRedisDataOpsValue() {
        String givenData = "data";
        long givenTimeout = 0;
        TimeUnit givenTimeUnit = TimeUnit.SECONDS;

        redisStoreService.addData(givenData, givenTimeout, givenTimeUnit);

        assertThat(spyValueOperations.set_key_argument).isEqualTo(stubRedisDataKeyProvider.generate());
        assertThat(spyValueOperations.set_timeout_argument).isEqualTo(givenTimeout);
        assertThat(spyValueOperations.set_timeUnit_argument).isEqualTo(givenTimeUnit);
        assertThat(spyValueOperations.set_value_argument.getData()).isEqualTo(givenData);
        assertThat(spyValueOperations.set_value_argument.getSearchCode()).isEqualTo(stubRedisDataKeyProvider.generate());
        assertThat(spyValueOperations.set_value_argument.getCreatedAt()).isEqualTo(stubLocalDateTimeProvider.now());
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

    @DisplayName("데이터 조회/결과")
    @Test
    void getData_returnValue() {
        String givenDataSignKey = "searchCode";
        LocalDateTime givenDateTime = LocalDateTime.now();
        spyValueOperations.get_returnValue = RedisDataFixture.anRedisData(givenDateTime)
                .setCreateAt(givenDateTime)
                .build();
        spyRedisTemplate.getExpire_returnValue = 1L;

        RedisStoreGetDataResponse<Serializable> response = redisStoreService.getData(givenDataSignKey);

        assertThat(response.getCode()).isEqualTo(givenDataSignKey);
        assertThat(response.getCreateAt()).isEqualTo(givenDateTime);
        assertThat(response.getTimeoutAt()).isEqualTo(stubLocalDateTimeProvider.now().plusSeconds(spyRedisTemplate.getExpire_returnValue));
    }

    @DisplayName("데이터 조회/패스")
    @Test
    void getData_passesArgument() {
        String givenDataSignKey = "searchCode";

        redisStoreService.getData(givenDataSignKey);

        assertThat(spyValueOperations.get_key_argument).isEqualTo(givenDataSignKey);
    }

    @DisplayName("데이터 조회/예외처리")
    @Test
    void getData_throwRedisDataNotFoundException() {
        String givenDataSignKey = "searchCode";

        spyValueOperations.get_returnValue = null;
        Assertions.assertThrows(RedisDataNotFoundException.class, () -> redisStoreService.getData(givenDataSignKey));
        spyValueOperations.get_returnValue = RedisDataFixture.anRedisData("").build();
        spyRedisTemplate.getExpire_returnValue = null;
        Assertions.assertThrows(RedisDataNotFoundException.class, () -> redisStoreService.getData(givenDataSignKey));
    }
}