package org.devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.devgraft.support.provider.StubDateProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceImplTest {
    private JwtProperties jwtProperties;
    private JwtServiceImpl jwtService;
    private StubDateProvider stubDateProvider;

    @BeforeEach
    void setUp() {
        stubDateProvider = new StubDateProvider();
        jwtProperties = new JwtProperties("JwtServiceImplTestAAAAAAAAAAA");
        jwtService = new JwtServiceImpl(jwtProperties, stubDateProvider);
    }

    @DisplayName("토큰 생성/결과")
    @Test
    void generateToken_returnValue() {
        String givenSub = "Sub";
        String givenAud = "Aud";
        String givenRole = "Role";
        int givenPeriodSecond = 3600;
        JwtGenerateRequest givenRequest = JwtGenerateRequest.of(givenSub, givenAud, givenRole, givenPeriodSecond);
        String jwt = issuedToken(givenSub, givenAud, givenRole, givenPeriodSecond);

        String result = jwtService.generateToken(givenRequest);

        assertThat(result).isEqualTo(jwt);
    }

    @DisplayName("getBody/결과")
    @Test
    void getBody_returnValue() {
        String token = issuedToken("Sub","Aud", "role", 3600);
        Claims givenClaims = Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Claims result = jwtService.getBody(token);

        assertThat(result).isEqualTo(givenClaims);
    }

    @DisplayName("토큰 확인/정상결과")
    @Test
    void verifyToken_returnValue() {
        String token = issuedToken("Sub","Aud", "role", 3600);

        boolean result = jwtService.verifyToken(token);

        assertThat(result).isTrue();
    }

    @DisplayName("토큰 확인/만료결과")
    @Test
    void verifyToken_Expired_returnValue() {
        String token = issuedToken("Sub", "Aud", "role", 0);

        boolean result = jwtService.verifyToken(token);

        assertThat(result).isFalse();
    }

    @DisplayName("사용자 식별값 조회/결과")
    @Test
    void getAud_returnValue() {
        String givenSub = "Sub";
        String givenAud = "Aud";
        String token = issuedToken(givenSub, givenAud, "role", 3600);

        String result = jwtService.getAud(token);

        assertThat(result).isEqualTo(givenAud);
    }

    @DisplayName("Subject 조회/결과")
    @Test
    void getSub_returnValue() {
        String givenSub = "Sub";
        String givenAud = "Aud";
        String token = issuedToken(givenSub, givenAud, "role", 3600);

        String result = jwtService.getSub(token);

        assertThat(result).isEqualTo(givenSub);
    }

    private String issuedToken(final String sub, final String aud, final String role, final long periodSecond) {
        Claims claims = Jwts.claims()
                .setSubject(sub)
                .setAudience(aud);

        claims.put("role", role);
        Date now = stubDateProvider.now();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + periodSecond * 1000))
                .signWith(jwtProperties.getSignKey())
                .compact();
    }
}