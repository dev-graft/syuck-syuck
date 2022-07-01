package org.devgraft.auth;

public interface JwtAuthService {
    JwtAuthResult refresh(final String accessToken, final String refresh);
    JwtAuthResult issue(final String identifyToken);
}
