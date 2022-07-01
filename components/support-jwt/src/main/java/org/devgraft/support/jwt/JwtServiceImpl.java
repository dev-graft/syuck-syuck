package org.devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.devgraft.support.provider.DateProvider;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {
    private final JwtProperties jwtProperties;
    private final DateProvider dateProvider;

    @Override
    public String generateToken(final JwtGenerateRequest request) {
        Claims claims = Jwts.claims()
                .setSubject(request.getSub())
                .setAudience(request.getAud());
        claims.put("role", request.getRole());
        Date now = dateProvider.now();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + request.getPeriodSecond() * 1000))
                .signWith(jwtProperties.getSignKey())
                .compact();
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            this.getBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    @Override
    public String getAud(String token) {
        return this.getBody(token)
                .getAudience();
    }

    @Override
    public String getSub(String token) {
        return this.getBody(token)
                .getSubject();
    }

    @Override
    public Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
