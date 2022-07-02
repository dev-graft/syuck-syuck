package org.devgraft.auth;

import io.jsonwebtoken.Jwts;
import org.devgraft.member.SpyMemberService;
import org.devgraft.support.jwt.SpyJwtService;
import org.devgraft.support.provider.StubSHA256Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceImplTest {
    private SpyMemberService spyMemberService;
    private SpyJwtService spyJwtService;
    private StubSHA256Provider stubSHA256Provider;
    private AuthService authService;
    public final String HEADER_TOKEN_PREFIX = "Bearer ";
    public final String ACCESS_TOKEN_SYNTAX = "Authorization";
    public final String REFRESH_TOKEN_SYNTAX = "refresh_token";

    @BeforeEach
    void setUp() {
        spyMemberService = new SpyMemberService();
        spyJwtService = new SpyJwtService();
        stubSHA256Provider = new StubSHA256Provider();
        authService = new AuthServiceImpl(spyMemberService, spyJwtService, stubSHA256Provider);
    }

    @DisplayName("사용자 로그인 용도 토큰 발급. identifyToken 필요.")
    @Test
    void issuedMemberAuthToken_test() {
        spyMemberService.getMemberId_returnValue = 2L;
        String givenIdentifyToken = "identifyToken";

        AuthResult jwtAuthResult = authService.issuedMemberAuthToken(givenIdentifyToken);

        assertThat(spyMemberService.getMemberId_identifyToken_argument).isEqualTo(givenIdentifyToken);
        assertThat(jwtAuthResult).isNotNull();
    }

    @DisplayName("토큰 재발급. refresh는 만료되어선 안된다.")
    @Test
    void refresh_test() {
        spyJwtService.getBody_returnValue = Jwts.claims()
                .setSubject(stubSHA256Provider.encrypt_returnValue)
                .setAudience("1");

        authService.refresh("access", "refresh");
    }

    @DisplayName("인증 토큰을 요청자에게 전달한다. access, refresh 모두 null일 수 없다.")
    @Test
    void injectAuthorization_test() {
        MockHttpServletResponse givenResponse = new MockHttpServletResponse();
        String givenAccessToken = "access";
        String givenRefresh = "refresh";

        authService.injectAuthorization(givenAccessToken, givenRefresh, givenResponse);

        assertThat(givenResponse.getHeader(ACCESS_TOKEN_SYNTAX)).isEqualTo(givenAccessToken);
        assertThat(Objects.requireNonNull(givenResponse.getCookie(REFRESH_TOKEN_SYNTAX)).getValue()).isEqualTo(givenRefresh);
    }

    @DisplayName("사용자의 요청 정보에서 인증 토큰을 갖고 온다.")
    @Test
    void exportAuthorization_test() {
        MockHttpServletRequest givenRequest = new MockHttpServletRequest();
        String givenRefresh = "refresh";
        String givenAccess = "access";
        String givenBearerAccess = HEADER_TOKEN_PREFIX.concat(givenAccess);
        Cookie givenCookie = new Cookie(REFRESH_TOKEN_SYNTAX, givenRefresh);
        givenRequest.addHeader(ACCESS_TOKEN_SYNTAX, givenBearerAccess);
        givenRequest.setCookies(givenCookie);

        Optional<AuthResult> authResultOpt = authService.exportAuthorization(givenRequest);

        assertThat(authResultOpt.isPresent()).isTrue();
        AuthResult authResult = authResultOpt.get();
        assertThat(authResult.getAccess()).isEqualTo(givenAccess);
        assertThat(authResult.getRefresh()).isEqualTo(givenRefresh);
    }

    @DisplayName("사용자의 요청 정보에서 인증 토큰이 없을 경우 Optional Empty 값을 반환한다.")
    @Test
    void exportAuthorization_emptyTest() {
        MockHttpServletRequest givenRequest = new MockHttpServletRequest();

        Optional<AuthResult> authResultOpt = authService.exportAuthorization(givenRequest);

        assertThat(authResultOpt.isPresent()).isFalse();
    }

    @DisplayName("사용자의 인증 정보를 삭제한다.")
    @Test
    void removeAuthorization_test() {
        MockHttpServletResponse givenResponse = new MockHttpServletResponse();

        authService.removeAuthorization(givenResponse);

        assertThat(Objects.requireNonNull(givenResponse.getCookie(REFRESH_TOKEN_SYNTAX)).getValue()).isNull();
    }
}