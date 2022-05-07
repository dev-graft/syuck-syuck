package org.devgraft.member.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.member.domain.GenderEnum;
import org.devgraft.member.domain.Member;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberAuthenticationInfoGetResponse {
    private final String id;
    private final String password;
    private final String nickName;
    private final GenderEnum gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updateAt;

    public MemberAuthenticationInfoGetResponse(Member member) {
        this.id = member.getId();
        this.password = member.getPassword();
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.createAt = member.getCreateAt();
        this.updateAt = member.getUpdateAt();
    }
}
