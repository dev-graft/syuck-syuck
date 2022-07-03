package org.devgraft.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberGetResponse {
    private final String email;
    private final String nickName;
    private final String profileImage;
    private final String stateMessage;

    public static MemberGetResponse of(final String email, final String nickName, final String profileImage, final String stateMessage) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(nickName, "nickName must not be null");
        Assert.notNull(profileImage, "profileImage must not be null");
        return new MemberGetResponse(email, nickName, profileImage, stateMessage);
    }
}
