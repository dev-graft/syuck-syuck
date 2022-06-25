package org.devgraft.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthorizationToken {
    private final String accessToken;
    private final String refreshToken;

    public static AuthorizationToken of(final String accessToken, final String refreshToken) {
        return new AuthorizationToken(accessToken, refreshToken);
    }
}
