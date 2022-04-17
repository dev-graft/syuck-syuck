package org.devgraft.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {
    StubUuidProvider stubUuidProvider;
    StubDateProvider stubDateProvider;
    JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        stubUuidProvider = new StubUuidProvider();
        stubDateProvider = new StubDateProvider();
        jwtTokenProvider = new JwtTokenProvider(stubUuidProvider, stubDateProvider);
    }

    @Test
    void generate_returnValue() {
        long givenValidity = 5L;
        long givenRefreshValidity = 12000000L;

        UUID uuid1 = stubUuidProvider.randomUUID();
        Date date1 = stubDateProvider.generate();
        Claims claims1 = Jwts.claims().setSubject(uuid1.toString());
        String accessToken = Jwts.builder()
                .setClaims(claims1)
                .setIssuedAt(date1)
                .setExpiration(new Date(date1.getTime() + givenValidity))
                .signWith(SignatureAlgorithm.HS256, "QVNEQVNEIUA=")
                .compact();

        UUID uuid2 = stubUuidProvider.randomUUID();
        Date date2 = stubDateProvider.generate();
        Claims claims = Jwts.claims().setSubject(uuid2.toString());
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date2)
                .setExpiration(new Date(date2.getTime() + givenRefreshValidity))
                .signWith(SignatureAlgorithm.HS256, "QVNEQVNEIUA=")
                .compact();

        stubUuidProvider.index = 0;

        JwtToken jwtToken = jwtTokenProvider.generate(givenValidity, givenRefreshValidity);

        assertThat(jwtToken.getAccessToken()).isEqualTo(accessToken);
        assertThat(jwtToken.getRefreshToken()).isEqualTo(refreshToken);
    }
}