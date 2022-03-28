package org.devgraft.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class Member {
    @Id
    private String id;
    private String nickName;
    private String password;
    private int gender;
    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Member(String id, String nickName, String password, int gender, LocalDateTime createAt, LocalDateTime updateAt) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.gender = gender;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Member create(String id, String password, String nickName, int gender) {
        return Member.builder()
                .nickName(nickName)
                .id(id)
                .password(password)
                .gender(gender)
                .build();
    }
}
