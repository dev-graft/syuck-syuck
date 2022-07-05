package org.devgraft.member;

public class MemberFixture {
    public static Member.MemberBuilder anMember() {
        return Member.builder()
                .email("email")
                .nickname("nickname")
                .profileImage("profileImage")
                .stateMessage("stateMessage");
    }
}
