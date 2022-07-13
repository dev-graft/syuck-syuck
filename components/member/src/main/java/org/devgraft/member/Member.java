package org.devgraft.member;

import lombok.AccessLevel;
import lombok.Builder;
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
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "identify_token")
    private String identifyToken;
    @Column(name = "state_message")
    private String stateMessage;

    @Builder(access = AccessLevel.PROTECTED)
    private Member(Long id, String email, String profileImage, String nickname, String identifyToken, String stateMessage) {
        this.id = id;
        this.email = email;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.identifyToken = identifyToken;
        this.stateMessage = stateMessage;
    }

    public static Member of(final String email, final String profileImage, final String nickname, final String identifyToken, final String stateMessage) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(nickname, "nickname must not be null");
        Assert.notNull(identifyToken, "identifyToken must not be null");
        return new Member(null, email, profileImage, nickname, identifyToken, stateMessage);
    }

    public void updateProfile(final String nickname, final String stateMessage) {
        Assert.notNull(nickname, "nickName must not be null");
        Assert.notNull(stateMessage, "stateMessage must not be null");
        this.nickname = nickname;
        this.stateMessage = stateMessage;
    }
}
