package org.devgraft.auth.service;

import org.devgraft.jwt.provider.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceImplTest {
    SpyJwtTokenProvider jwtTokenProvider;
    AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new SpyJwtTokenProvider();
        authService = new AuthServiceImpl(jwtTokenProvider);
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
        authService.generateToken(givenRequest);

        assertThat(jwtTokenProvider.generate_validity_argument).isEqualTo(givenRequest.getValidity());
        assertThat(jwtTokenProvider.generate_refreshValidity_argument).isEqualTo(givenRequest.getRefreshValidity());
    }
}