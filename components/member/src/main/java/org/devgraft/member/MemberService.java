package org.devgraft.member;

public interface MemberService {
    boolean alreadyJoin(final String identifyToken);
    void join(final MemberJoinRequest request);
    Long getMemberId(final String identifyToken);
    MemberGetResponse getMember(final Long memberId);
    void updateMember(final Long memberId, final MemberUpdateRequest request);
}
