package org.devgraft.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthResult {
    private final String access;
    private final String refresh;

    public static AuthResult of(final String access, final String refresh) {
        Assert.notNull(access, "access must not be null");
        Assert.notNull(refresh, "refresh must not be null");
        return new AuthResult(access, refresh);
    }
}
