package org.devgraft.member;

public class MemberFixture {
    public static Member.MemberBuilder anMember() {
        return Member.builder()
                .email("email")
                .nickName("nickName")
                .profileImage("profileImage")
                .stateMessage("stateMessage");
    }
}
