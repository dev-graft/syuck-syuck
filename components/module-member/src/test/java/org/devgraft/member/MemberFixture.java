package org.devgraft.member;

import org.devgraft.member.domain.Member;

import java.time.LocalDateTime;

public class MemberFixture {
    public static Member.MemberBuilder anMember() {
        return Member.builder()
                .id("id")
                .nickName("nickName")
                .gender(0)
                .password("password")
                .createAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now());
    }
}
