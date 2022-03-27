package org.devgraft.member.service;

public interface MemberService {
    MemberJoinResponse joinMember(MemberJoinRequest request);
    MemberGetResponse getMember(String id);
    void modifyMember(MemberModifyRequest request);
}
