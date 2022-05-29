package org.devgraft.authstore.service;

import java.util.Optional;

public interface AuthTokenService {
    TokenGenerateResponse generateToken(TokenGenerateRequest request);
    void deleteToken(String accessToken, String refreshToken);

    Optional<AuthInformation> getAuthInformation(String accessToken);

    Optional<TokenReIssueResponse> reIssueToken(String accessToken, String refreshToken);
}
