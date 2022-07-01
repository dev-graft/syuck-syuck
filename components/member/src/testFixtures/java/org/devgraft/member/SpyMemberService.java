package org.devgraft.member;

public class SpyMemberService implements MemberService {
    public String getMemberId_identifyToken_argument;
    public Long getMemberId_returnValue = 1L;
    @Override
    public boolean alreadyJoin(String identifyToken) {
        return false;
    }

    @Override
    public void join(MemberJoinRequest request) {

    }

    @Override
    public Long getMemberId(String identifyToken) {
        getMemberId_identifyToken_argument = identifyToken;
        return getMemberId_returnValue;
    }

    @Override
    public MemberGetResponse getMember(Long memberId) {
        return null;
    }
}
