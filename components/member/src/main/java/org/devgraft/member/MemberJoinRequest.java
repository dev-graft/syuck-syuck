package org.devgraft.member;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class MemberJoinRequest {
    private final String profileImage;
    private final String nickName;
    private final String identifyToken;
    private final String stateMessage;

    public MemberJoinRequest(String profileImage, String nickName, String identifyToken, String stateMessage) {
        Assert.notNull(nickName, "nickName must not be null");
        Assert.notNull(identifyToken, "identifyToken must not be null");
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.identifyToken = identifyToken;
        this.stateMessage = stateMessage;
    }
}
