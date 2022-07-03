package org.devgraft.support.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpyJwtService implements JwtService {
    public ArrayList<String> getGetBody_token_arguments = new ArrayList<>();
    public String getBody_token_argument;
    public Map<String, Claims> getBody_returnValues = new HashMap<>();
    public Claims getBody_returnValue = Jwts.claims();
    public Map<String, Boolean> verifyToken_returnValues = new HashMap<>();
    public boolean verifyToken_returnValue = true;
    @Override
    public String generateToken(JwtGenerateRequest request) {
        return "token";
    }

    @Override
    public boolean verifyToken(String token) {
        return verifyToken_returnValues.getOrDefault(token, verifyToken_returnValue);
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
        getGetBody_token_arguments.add(token);
        this.getBody_token_argument = token;
        return getBody_returnValues.getOrDefault(token, getBody_returnValue);
    }
}
