package org.devgraft.auth.service;

import org.devgraft.auth.store.service.AuthDataInformation;
import org.devgraft.auth.store.service.AuthTokenService;
import org.devgraft.auth.store.service.TokenGenerateRequest;
import org.devgraft.auth.store.service.TokenGenerateResponse;
import org.devgraft.auth.store.service.TokenReIssueResponse;

import java.util.Optional;

public class SpyAuthTokenService implements AuthTokenService {
    @Override
    public TokenGenerateResponse generateToken(TokenGenerateRequest request) {
        return null;
    }

    @Override
    public void deleteToken(String accessToken, String refreshToken) {

    }

    @Override
    public Optional<AuthDataInformation> getAuthDataInformation(String accessToken) {
        return Optional.empty();
    }

    @Override
    public Optional<TokenReIssueResponse> reIssueToken(String accessToken, String refreshToken) {
        return Optional.empty();
    }
}
