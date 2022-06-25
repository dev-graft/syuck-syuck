package org.devgraft.support.jwt;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtGenerateRequest {
    private final String aud;
    private final String role;
    private final long periodSecond;

    public static JwtGenerateRequest of(final String aud, final String role, final long periodSecond) {
        Assert.notNull(aud, "aud must not be null");
        Assert.notNull(role, "role must not be null");
        return new JwtGenerateRequest(aud, role, periodSecond);
    }
}
