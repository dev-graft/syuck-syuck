package org.devgraft.auth;

import io.jsonwebtoken.Jwts;
import org.devgraft.member.SpyMemberService;
import org.devgraft.support.jwt.SpyJwtService;
import org.devgraft.support.provider.StubSHA256Provider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAuthServiceImplTest {
    private StubSHA256Provider stubSHA256Provider;
    private SpyMemberService spyMemberService;
    private SpyJwtService spyJwtService;
    private JwtAuthService jwtAuthService;

    @BeforeEach
    void setUp() {
        stubSHA256Provider = new StubSHA256Provider();
        spyMemberService = new SpyMemberService();
        spyJwtService = new SpyJwtService();
        jwtAuthService = new JwtAuthServiceImpl(stubSHA256Provider, spyMemberService, spyJwtService);
    }

    @DisplayName("사용자 로그인 용도 토큰 발급. identifyToken 필요.")
    @Test
    void issue_test() {
        spyMemberService.getMemberId_returnValue = 2L;
        String givenIdentifyToken = "identifyToken";

        JwtAuthResult jwtAuthResult = jwtAuthService.issue(givenIdentifyToken);

        assertThat(spyMemberService.getMemberId_identifyToken_argument).isEqualTo(givenIdentifyToken);
        assertThat(jwtAuthResult).isNotNull();
    }

    @DisplayName("토큰 재발급. refresh는 만료되어선 안된다.")
    @Test
    void refresh_test() {
        spyJwtService.getBody_returnValue = Jwts.claims()
                .setSubject(stubSHA256Provider.encrypt_returnValue)
                .setAudience("1");
        jwtAuthService.refresh("access", "refresh");
    }
}