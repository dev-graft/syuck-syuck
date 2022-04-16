package org.devgraft.auth.service;

import org.devgraft.auth.service.TokenGenerateRequest;

public interface AuthService {
    void generateToken(TokenGenerateRequest request);
}
