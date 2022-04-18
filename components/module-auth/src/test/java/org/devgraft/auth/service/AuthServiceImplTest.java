package org.devgraft.auth.service;

import org.devgraft.auth.jwt.JwtToken;
import org.devgraft.auth.provider.StubLocalDateTimeProvider;
import org.devgraft.jwt.provider.StubUuidProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceImplTest {
    SpyJwtTokenProvider jwtTokenProvider;
    AuthServiceImpl authService;
    SpyRedisTemplate redisTemplate;
    StubLocalDateTimeProvider localDateTimeProvider;
    StubUuidProvider uuidProvider;

    @BeforeEach
    void setUp() {
        redisTemplate = new SpyRedisTemplate();
        jwtTokenProvider = new SpyJwtTokenProvider();
        localDateTimeProvider = new StubLocalDateTimeProvider();
        uuidProvider = new StubUuidProvider();
        authService = new AuthServiceImpl(jwtTokenProvider, redisTemplate, localDateTimeProvider, uuidProvider);
    }

    @Test
    void generateToken_returnValue() {
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(List.of(), null, 10000L, 100000L);
        jwtTokenProvider.generate_returnValue = new JwtToken("accessToken", "refreshToken");
        TokenGenerateResponse tokenGenerateResponse = authService.generateToken(givenRequest);

        assertThat(tokenGenerateResponse.getAccessToken()).isEqualTo(jwtTokenProvider.generate_returnValue.getAccessToken());
        assertThat(tokenGenerateResponse.getRefreshToken()).isEqualTo(jwtTokenProvider.generate_returnValue.getRefreshToken());
    }

    @Test
    void generateToken_passesRequestToJwtTokenProvider() {
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(List.of(), null, 10000L, 100000L);
        jwtTokenProvider.generate_returnValue = new JwtToken("accessToken", "refreshToken");
        authService.generateToken(givenRequest);

        assertThat(jwtTokenProvider.generate_validity_argument).isEqualTo(givenRequest.getValidity());
        assertThat(jwtTokenProvider.generate_refreshValidity_argument).isEqualTo(givenRequest.getRefreshValidity());
    }

    @Test
    void generateToken_passesRequestToRedisTemplate() {
        long givenValidity = 10000L;
        long givenRefreshValidity = 100000L;
        List<@Pattern(regexp = "^ROLE_\\w{1,20}$") String> givenRoles = List.of("ROLE_TEST");
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(givenRoles, new HashMap<>(), givenValidity, givenRefreshValidity);
        jwtTokenProvider.generate_returnValue = new JwtToken("accessToken", "refreshToken");
        authService.generateToken(givenRequest);

        assertThat(redisTemplate.opsForValue_wasCall).isTrue();
        assertThat(redisTemplate.opsForHash_wasCall).isTrue();
        assertThat(redisTemplate.spyValueOperations.set_key_argument.get(0)).isEqualTo(jwtTokenProvider.generate_returnValue.getAccessToken());
        assertThat(redisTemplate.spyValueOperations.set_value_argument.get(0)).isEqualTo(jwtTokenProvider.generate_returnValue.getRefreshToken());
        assertThat(redisTemplate.spyValueOperations.set_key_argument.get(1)).isEqualTo(jwtTokenProvider.generate_returnValue.getRefreshToken());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getAccessToken()).isEqualTo(jwtTokenProvider.generate_returnValue.getAccessToken());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getRefreshToken()).isEqualTo(jwtTokenProvider.generate_returnValue.getRefreshToken());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getValidity()).isEqualTo(givenRequest.getValidity());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getRefreshValidity()).isEqualTo(givenRequest.getRefreshValidity());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getRoles()).isEqualTo(givenRequest.getRoles());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getDataSignKey()).isEqualTo(uuidProvider.randomUUID.get(0).toString());
        assertThat(((AuthInformation)redisTemplate.spyValueOperations.set_value_argument.get(1)).getCreateAt()).isEqualTo(localDateTimeProvider.now());
        assertThat(redisTemplate.spyHashOperations.putAll_key_argument).isEqualTo(uuidProvider.randomUUID.get(0).toString());
        assertThat(redisTemplate.spyHashOperations.putAll_value_argument).isEqualTo(givenRequest.getData());
        assertThat(redisTemplate.expire_key_argument.get(0)).isEqualTo(jwtTokenProvider.generate_returnValue.getAccessToken());
        assertThat(redisTemplate.expire_key_argument.get(1)).isEqualTo(jwtTokenProvider.generate_returnValue.getRefreshToken());
        assertThat(redisTemplate.expire_key_argument.get(2)).isEqualTo(uuidProvider.randomUUID.get(0).toString());
        assertThat(redisTemplate.expire_timeout_argument.get(0)).isEqualTo(givenRequest.getValidity());
        assertThat(redisTemplate.expire_timeout_argument.get(1)).isEqualTo(givenRequest.getRefreshValidity());
        assertThat(redisTemplate.expire_timeout_argument.get(2)).isEqualTo(givenRequest.getRefreshValidity());
        assertThat(redisTemplate.expire_unit_argument.get(0)).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(redisTemplate.expire_unit_argument.get(1)).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(redisTemplate.expire_unit_argument.get(2)).isEqualTo(TimeUnit.MILLISECONDS);

    }
}