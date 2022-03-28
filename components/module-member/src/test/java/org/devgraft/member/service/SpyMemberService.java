package org.devgraft.member.service;

import java.time.LocalDateTime;

public class SpyMemberService implements MemberService {
    public MemberJoinRequest argument_request;
    public String argument_id;
    public MemberGetResponse getMember_returnValue;
    public MemberModifyRequest modifyMember_argument;

    @Override
    public MemberJoinResponse joinMember(MemberJoinRequest request) {
        this.argument_request = request;
        return new MemberJoinResponse(request.getNickName(), request.getId(), request.getGender(), LocalDateTime.of(2022, 3, 25, 9, 32, 0));
    }

    @Override
    public MemberGetResponse getMember(String id) {
        this.argument_id = id;
        return getMember_returnValue;
    }

    @Override
    public void modifyMember(MemberModifyRequest request) {
        this.modifyMember_argument = request;
    }
}
