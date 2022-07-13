package org.devgraft.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberJoinRequest {
    private String email;
    private String profileImage;
    private String nickname;
    private String identifyToken;
    private String stateMessage;

    private MemberJoinRequest(final String email, final String profileImage, final String nickname, final String identifyToken, final String stateMessage) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(nickname, "nickname must not be null");
        Assert.notNull(identifyToken, "identifyToken must not be null");
        this.email = email;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.identifyToken = identifyToken;
        this.stateMessage = stateMessage;
    }

    public static MemberJoinRequest of(final String email, final String profileImage, final String nickname, final String identifyToken, final String stateMessage) {
        return new MemberJoinRequest(email, profileImage, nickname, identifyToken, stateMessage);
    }
}
