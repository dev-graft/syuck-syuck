package org.devgraft.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.devgraft.jpa.BaseEntity;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "identify_token")
    private String identifyToken;
    @Column(name = "state_message")
    private String stateMessage;

    private Member(Long id, String profileImage, String nickName, String identifyToken, String stateMessage) {
        this.id = id;
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.identifyToken = identifyToken;
        this.stateMessage = stateMessage;
    }

    public static Member of(final String profileImage, final String nickName, final String identifyToken, final String stateMessage) {
        Assert.notNull(nickName, "nickName must not be null");
        Assert.notNull(identifyToken, "identifyToken must not be null");
        return new Member(null, profileImage, nickName, identifyToken, stateMessage);
    }
}
