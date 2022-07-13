package org.devgraft.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberUpdateRequest {
    private String nickname;
    private String stateMessage;

    private MemberUpdateRequest(final String nickname, final String stateMessage) {
        Assert.notNull(nickname, "nickname must not be null");
        Assert.notNull(nickname, "stateMessage must not be null");
        this.nickname = nickname;
        this.stateMessage = stateMessage;
    }

    public static MemberUpdateRequest of(final String nickname, final String stateMessage) {
        return new MemberUpdateRequest(nickname, stateMessage);
    }
}
