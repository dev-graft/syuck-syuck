package org.devgraft.auth.service;

import org.devgraft.auth.service.TokenGenerateRequest;
import org.devgraft.jwt.provider.JwtToken;

public interface AuthService {
    TokenGenerateResponse generateToken(TokenGenerateRequest request);
}
