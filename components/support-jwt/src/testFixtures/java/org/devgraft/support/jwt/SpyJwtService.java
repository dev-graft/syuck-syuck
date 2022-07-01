package org.devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class SpyJwtService implements JwtService {
    public String getBody_token_argument;
    public Claims getBody_returnValue = Jwts.claims();
    @Override
    public String generateToken(JwtGenerateRequest request) {
        return "token";
    }

    @Override
    public boolean verifyToken(String token) {
        return false;
    }

    @Override
    public String getAud(String token) {
        return null;
    }

    @Override
    public String getSub(String token) {
        return null;
    }

    @Override
    public Claims getBody(String token) {
        this.getBody_token_argument = token;
        return getBody_returnValue;
    }
}
