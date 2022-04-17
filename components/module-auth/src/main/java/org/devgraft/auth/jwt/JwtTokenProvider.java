package org.devgraft.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.devgraft.auth.provider.DateProvider;
import org.devgraft.auth.provider.UuidProvider;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final UuidProvider uuidProvider;
    private final DateProvider dateProvider;

    public JwtToken generate(Long validity, Long refreshValidity) {
        Date date = dateProvider.generate();

        String accessToken = _generate(uuidProvider.randomUUID(), date, validity);
        String refreshToken = _generate(uuidProvider.randomUUID(), date, refreshValidity);

        return new JwtToken(accessToken, refreshToken);
    }

    private String _generate(UUID signKey, Date date, Long validity) {
        Claims claims = Jwts.claims().setSubject(signKey.toString());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + validity))
                .signWith(SignatureAlgorithm.HS256, "QVNEQVNEIUA=")
                .compact();
    }
}