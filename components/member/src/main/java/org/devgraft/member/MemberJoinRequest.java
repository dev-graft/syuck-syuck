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
    private String nickName;
    private String identifyToken;
    private String stateMessage;

    private MemberJoinRequest(String email, String profileImage, String nickName, String identifyToken, String stateMessage) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(nickName, "nickName must not be null");
        Assert.notNull(identifyToken, "identifyToken must not be null");
        this.email = email;
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.identifyToken = identifyToken;
        this.stateMessage = stateMessage;
    }

    public static MemberJoinRequest of(final String email, final String profileImage, final String nickName, final String identifyToken, final String stateMessage) {
        return new MemberJoinRequest(email, profileImage, nickName, identifyToken, stateMessage);
    }
}
