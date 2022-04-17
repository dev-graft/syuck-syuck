package org.devgraft.auth.service;

public interface AuthService {
    TokenGenerateResponse generateToken(TokenGenerateRequest request);
}
