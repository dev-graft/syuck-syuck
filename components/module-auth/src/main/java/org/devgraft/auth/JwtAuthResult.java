package org.devgraft.auth;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JwtAuthResult {
    private final String access;
    private final String refresh;

    public static JwtAuthResult of(String access, String refresh) {
        Assert.notNull(access, "access must not be null");
        Assert.notNull(refresh, "refresh must not be null");
        return new JwtAuthResult(access, refresh);
    }
}
