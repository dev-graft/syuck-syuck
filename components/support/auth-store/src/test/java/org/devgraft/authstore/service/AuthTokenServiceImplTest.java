package org.devgraft.authstore.service;

import org.devgraft.authstore.JwtTokenFixture;
import org.devgraft.authstore.provider.SpyJwtTokenProvider;
import org.devgraft.authstore.provider.StubLocalDateTimeProvider;
import com.dreamsecurity.token.jwt.domain.JwtToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class AuthTokenServiceImplTest {
    SpyJwtTokenProvider spyJwtTokenProvider;
    AuthTokenServiceImpl authService;
    SpyRedisTemplate spyRedisTemplate;
    StubLocalDateTimeProvider stubLocalDateTimeProvider;
    SpyRedisStoreService spyRedisStoreService;

    @BeforeEach
    void setUp() {
        spyRedisTemplate = new SpyRedisTemplate();
        spyJwtTokenProvider = new SpyJwtTokenProvider();
        stubLocalDateTimeProvider = new StubLocalDateTimeProvider();
        spyRedisStoreService = new SpyRedisStoreService();

        authService = new AuthTokenServiceImpl(spyJwtTokenProvider, spyRedisTemplate, stubLocalDateTimeProvider, spyRedisStoreService);
    }

    @Test
    void generateToken_returnValue() {
        JwtToken givenJwtToken = JwtTokenFixture.anToken();
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(new ArrayList<>(), null,
                givenJwtToken.getInformation().getAccessTokenValidity(),
                givenJwtToken.getInformation().getRefreshTokenValidity());
        spyJwtTokenProvider.generate_returnValue = givenJwtToken;

        TokenGenerateResponse tokenGenerateResponse = authService.generateToken(givenRequest);

        assertThat(tokenGenerateResponse.getAccessToken()).isEqualTo(spyJwtTokenProvider.generate_returnValue.accessToken());
        assertThat(tokenGenerateResponse.getRefreshToken()).isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
    }

    @Test
    void generateToken_passesDataToRedisStoreService() {
        JwtToken givenJwtToken = JwtTokenFixture.anToken();
        HashMap<String, Object> givenData = new HashMap<>();
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(new ArrayList<>(), givenData,
                givenJwtToken.getInformation().getAccessTokenValidity(),
                givenJwtToken.getInformation().getRefreshTokenValidity());
        spyJwtTokenProvider.generate_returnValue = givenJwtToken;

        authService.generateToken(givenRequest);

        assertThat(spyRedisStoreService.addData_data_argument).isEqualTo(givenData);
    }

    @Test
    void generateToken_passesRequestToJwtTokenProvider() {
        JwtToken givenJwtToken = JwtTokenFixture.anToken();
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(new ArrayList<>(), null,
                givenJwtToken.getInformation().getAccessTokenValidity(),
                givenJwtToken.getInformation().getRefreshTokenValidity());
        spyJwtTokenProvider.generate_returnValue = givenJwtToken;

        authService.generateToken(givenRequest);

        assertThat(spyJwtTokenProvider.generate_validity_argument).isEqualTo(givenRequest.getValidity());
        assertThat(spyJwtTokenProvider.generate_refreshValidity_argument).isEqualTo(givenRequest.getRefreshValidity());
    }

    @Test
    void generateToken_passesRequestToRedisTemplate() {
        List<@Pattern(regexp = "^ROLE_\\w{1,20}$") String> givenRoles = Collections.singletonList("ROLE_TEST");
        JwtToken givenJwtToken = JwtTokenFixture.anToken();
        TokenGenerateRequest givenRequest = new TokenGenerateRequest(givenRoles, new HashMap<>(),
                givenJwtToken.getInformation().getAccessTokenValidity(),
                givenJwtToken.getInformation().getRefreshTokenValidity());
        spyJwtTokenProvider.generate_returnValue = givenJwtToken;

        authService.generateToken(givenRequest);

        assertThat(spyRedisTemplate.opsForValue_wasCall).isTrue();
        assertThat(spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.accessToken()))
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRoles())
                .isEqualTo(givenRequest.getRoles());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getDataSignKey())
                .isEqualTo(spyRedisStoreService.addData_returnValue);
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getAccessTokenValidity())
                .isEqualTo(givenRequest.getValidity());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRefreshTokenValidity())
                .isEqualTo(givenRequest.getRefreshValidity());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getAccessToken())
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.accessToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRefreshToken())
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getCreateAt())
                .isEqualTo(stubLocalDateTimeProvider.now());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getUpdateAt())
                .isEqualTo(stubLocalDateTimeProvider.now());

        assertThat(spyRedisTemplate.expire_validity_map.get(spyJwtTokenProvider.generate_returnValue.accessToken())).isEqualTo(givenRequest.getValidity());
        assertThat(spyRedisTemplate.expire_validity_map.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).isEqualTo(givenRequest.getRefreshValidity());
        assertThat(spyRedisTemplate.expire_timeUnit_map.get(spyJwtTokenProvider.generate_returnValue.accessToken())).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(spyRedisTemplate.expire_timeUnit_map.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).isEqualTo(TimeUnit.MILLISECONDS);
    }

    @Test
    void deleteToken_passesTokenToRedisTemplate() {
        String givenAccessToken = "accessToken";
        String givenRefreshToken = "refreshToken";

        authService.deleteToken(givenAccessToken, givenRefreshToken);

        assertThat(spyRedisTemplate.delete_argument).contains(givenAccessToken, givenRefreshToken);
    }

    @Test
    void getAuthInformation_returnValue() {
        String givenToken = "accessToken";
        String givenRefreshToken = "refreshToken";
        long givenAccessTokenValidity = 10000L;
        long givenRefreshValidity = 100000L;
        AuthInformation givenAuthInformation = new AuthInformation(Collections.singletonList("ROLE_ADMIN"), "dataSignKey", givenAccessTokenValidity, givenRefreshValidity, givenToken, givenRefreshToken, LocalDateTime.now(), LocalDateTime.now());
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenToken, givenRefreshToken);
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);

        Optional<AuthInformation> authInformationOpt = authService.getAuthInformation(givenToken);

        assertThat(authInformationOpt.isPresent()).isTrue();

        AuthInformation authInformation = authInformationOpt.get();
        assertThat(authInformation.getDataSignKey()).isEqualTo(givenAuthInformation.getDataSignKey());
        assertThat(authInformation.getRoles()).isEqualTo(givenAuthInformation.getRoles());
        assertThat(authInformation.getDataSignKey()).isEqualTo(givenAuthInformation.getDataSignKey());
    }

    @Test
    void getAuthInformation_passesAccessToken_To_IsExpiration_Of_JwtTokenProvider() {
        String givenToken = "accessToken";
        spyJwtTokenProvider.isExpiration_returnValue.put(givenToken, true);

        Optional<AuthInformation> authInformation = authService.getAuthInformation(givenToken);

        assertThat(spyJwtTokenProvider.isExpiration_token_argument).contains(givenToken);
        assertThat(authInformation.isPresent()).isFalse();
    }

    @Test
    void getAuthInformation_returnValue_Empty_To_passesAccessToken() {
        String givenToken = "accessToken";

        Optional<AuthInformation> authInformation = authService.getAuthInformation(givenToken);

        assertThat(spyRedisTemplate.spyValueOperations.get_argument).contains(givenToken);
        assertThat(authInformation.isPresent()).isFalse();
    }

    @Test
    void getAuthInformation_returnValue_Empty_To_passesRefreshToken() {
        String givenToken = "accessToken";
        String givenRefresh = "refresh";
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenToken, givenRefresh);

        Optional<AuthInformation> authInformation = authService.getAuthInformation(givenToken);

        assertThat(spyRedisTemplate.spyValueOperations.get_argument).contains(givenToken, givenRefresh);
        assertThat(authInformation.isPresent()).isFalse();
    }

    @Test
    void reIssueToken_returnValue() {
        String givenAccessToken = "AccessToken";
        String givenRefreshToken = "refreshToken";
        long givenAccessTokenValidity = 10000L;
        long givenRefreshTokenValidity = 100000L;
        AuthInformation givenAuthInformation = new AuthInformation(null, null, givenAccessTokenValidity, givenRefreshTokenValidity, givenAccessToken, null, null, null);
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);
        spyJwtTokenProvider.isExpiration_returnValue.put(givenAccessToken, true);
        spyJwtTokenProvider.generate_returnValue = JwtTokenFixture.anToken();

        Optional<TokenReIssueResponse> response = authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getAccessToken()).isEqualTo(spyJwtTokenProvider.generate_returnValue.accessToken());
        assertThat(response.get().getRefreshToken()).isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
    }

    @Test
    void reIssueToken_passesRefreshTokenToJwtTokenProvider() {
        String givenRefreshToken = "refreshToken";
        spyJwtTokenProvider.isExpiration_returnValue.put(givenRefreshToken, true);

        authService.reIssueToken(null, givenRefreshToken);

        assertThat(spyJwtTokenProvider.isExpiration_token_argument).contains(givenRefreshToken);
    }

    @Test
    void reIssueToken_refreshTokenIsExpirationTheReturnValueEmpty() {
        String givenRefreshToken = "refreshToken";
        spyJwtTokenProvider.isExpiration_returnValue.put(givenRefreshToken, true);

        Optional<TokenReIssueResponse> responseOpt = authService.reIssueToken(null, givenRefreshToken);

        assertThat(responseOpt.isPresent()).isFalse();
    }

    @Test
    void reIssueToken_nullOfOpValueGet_The_ReturnValueEmpty() {
        String givenRefreshToken = "refreshToken";

        Optional<TokenReIssueResponse> response = authService.reIssueToken(null, givenRefreshToken);

        assertThat(response.isPresent()).isFalse();
    }

    @Test
    void reIssueToken_accessToken_NotEquals_AccessTokenOfAuthInformation_The_ReturnValueEmpty() {
        String givenAccessToken = "FakeAccessToken";
        String givenRefreshToken = "refreshToken";
        AuthInformation givenAuthInformation = new AuthInformation(null, null, null, null, "AccessToken", null, null, null);
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);

        Optional<TokenReIssueResponse> response = authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(response.isPresent()).isFalse();
    }

    @Test
    void reIssueToken_passesAccessToken_To_IsExpiration_Of_JwtTokenProvider() {
        String givenAccessToken = "AccessToken";
        String givenRefreshToken = "refreshToken";
        AuthInformation givenAuthInformation = new AuthInformation(null, null, null, null, givenAccessToken, null, null, null);
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);
        spyJwtTokenProvider.isExpiration_returnValue.put(givenAccessToken, false);

        Optional<TokenReIssueResponse> response = authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getAccessToken()).isEqualTo(givenAccessToken);
        assertThat(response.get().getRefreshToken()).isEqualTo(givenRefreshToken);
    }

    @Test
    void reIssueToken_passesValidity_To_Generate_Of_JwtTokenProvider() {
        String givenAccessToken = "AccessToken";
        String givenRefreshToken = "refreshToken";
        long givenAccessTokenValidity = 10000L;
        long givenRefreshValidity = 100000L;
        AuthInformation givenAuthInformation = new AuthInformation(null, null, givenAccessTokenValidity, givenRefreshValidity, givenAccessToken, null, null, null);
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);
        spyJwtTokenProvider.isExpiration_returnValue.put(givenAccessToken, true);
        spyJwtTokenProvider.generate_returnValue = JwtTokenFixture.anToken();

        authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(spyJwtTokenProvider.generate_validity_argument).isEqualTo(givenAccessTokenValidity);
        assertThat(spyJwtTokenProvider.generate_refreshValidity_argument).isEqualTo(givenRefreshValidity);
    }

    @Test
    void reIssueToken_passesToken_To_Set_Of_OpValue() {
        String givenAccessToken = "AccessToken";
        String givenRefreshToken = "refreshToken";
        long givenAccessTokenValidity = 10000L;
        long givenRefreshValidity = 100000L;
        AuthInformation givenAuthInformation = new AuthInformation(Collections.singletonList("ROLE_ADMIN"), "dataSignKey", givenAccessTokenValidity, givenRefreshValidity, givenAccessToken, givenRefreshToken, LocalDateTime.now(), LocalDateTime.now());
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);
        spyJwtTokenProvider.isExpiration_returnValue.put(givenAccessToken, true);
        spyJwtTokenProvider.generate_returnValue = JwtTokenFixture.anToken();

        authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.accessToken()))
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRoles())
                .isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getRoles());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getDataSignKey())
                .isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getDataSignKey());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getAccessTokenValidity())
                .isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getAccessTokenValidity());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRefreshTokenValidity())
                .isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getRefreshTokenValidity());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getAccessToken())
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.accessToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getRefreshToken())
                .isEqualTo(spyJwtTokenProvider.generate_returnValue.refreshToken());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getCreateAt())
                .isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getCreateAt());
        assertThat(((AuthInformation)spyRedisTemplate.spyValueOperations.set_argument.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).getUpdateAt())
                .isEqualTo(stubLocalDateTimeProvider.now());
    }

    @Test
    void reIssue_passesArgs_To_Delete_And_Expire_Of_RedisTemplate() {
        String givenAccessToken = "AccessToken";
        String givenRefreshToken = "refreshToken";
        long givenAccessTokenValidity = 10000L;
        long givenRefreshValidity = 100000L;
        AuthInformation givenAuthInformation = new AuthInformation(Collections.singletonList("ROLE_ADMIN"), "dataSignKey", givenAccessTokenValidity, givenRefreshValidity, givenAccessToken, givenRefreshToken, LocalDateTime.now(), LocalDateTime.now());
        spyRedisTemplate.spyValueOperations.get_returnValue.put(givenRefreshToken, givenAuthInformation);
        spyJwtTokenProvider.isExpiration_returnValue.put(givenAccessToken, true);
        spyJwtTokenProvider.generate_returnValue = JwtTokenFixture.anToken();

        authService.reIssueToken(givenAccessToken, givenRefreshToken);

        assertThat(spyRedisTemplate.delete_argument).contains(givenAccessToken, givenRefreshToken);

        assertThat(spyRedisTemplate.expire_validity_map.get(spyJwtTokenProvider.generate_returnValue.accessToken())).isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getAccessTokenValidity());
        assertThat(spyRedisTemplate.expire_validity_map.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).isEqualTo(((AuthInformation)spyRedisTemplate.spyValueOperations.get_returnValue.get(givenRefreshToken)).getRefreshTokenValidity());
        assertThat(spyRedisTemplate.expire_timeUnit_map.get(spyJwtTokenProvider.generate_returnValue.accessToken())).isEqualTo(TimeUnit.MILLISECONDS);
        assertThat(spyRedisTemplate.expire_timeUnit_map.get(spyJwtTokenProvider.generate_returnValue.refreshToken())).isEqualTo(TimeUnit.MILLISECONDS);
    }
}