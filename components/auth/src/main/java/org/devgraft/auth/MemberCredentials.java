package org.devgraft.auth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberCredentials {
    private final Long memberId;
    private final String role;

    public static MemberCredentials of(final Long memberId, final String role) {
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(role, "role must not be null");
        return new MemberCredentials(memberId, role);
    }
}
