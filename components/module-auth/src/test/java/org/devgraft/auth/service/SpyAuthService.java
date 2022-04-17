package org.devgraft.auth.service;

public class SpyAuthService implements AuthService {
    public TokenGenerateRequest generateToken_argument;
    public TokenGenerateResponse generateToken_returnValue = new TokenGenerateResponse("accessToken1", "refresh");

    @Override
    public TokenGenerateResponse generateToken(TokenGenerateRequest request) {
        this.generateToken_argument = request;
        return generateToken_returnValue;
    }
}
