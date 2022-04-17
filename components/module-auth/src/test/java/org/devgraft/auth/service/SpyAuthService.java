package org.devgraft.auth.service;

public class SpyAuthService implements AuthService {
    public TokenGenerateRequest generateToken_argument;

    @Override
    public TokenGenerateResponse generateToken(TokenGenerateRequest request) {
        this.generateToken_argument = request;
        return null;
    }
}
