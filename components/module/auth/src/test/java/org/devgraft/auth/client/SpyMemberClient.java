package org.devgraft.auth.client;

import org.devgraft.client.member.GenderEnum;
import org.devgraft.client.member.MemberAuthenticationInfoGetResponse;
import org.devgraft.client.member.MemberClient;
import org.devgraft.client.member.MemberGetResponse;
import org.devgraft.client.member.MemberJoinRequest;
import org.devgraft.client.member.MemberJoinResponse;
import org.devgraft.client.member.MemberModifyRequest;

public class SpyMemberClient implements MemberClient {
    public String getMemberAuthenticationInfo_argument;
    public MemberAuthenticationInfoGetResponse getMemberAuthenticationInfo_returnValue =
            new MemberAuthenticationInfoGetResponse("id", "password", "nickName", GenderEnum.Male, null, null);

    @Override
    public MemberJoinResponse joinMember(MemberJoinRequest request) {
        return null;
    }

    @Override
    public MemberGetResponse searchMember(String id) {
        return null;
    }

    @Override
    public void updateMember(String id, MemberModifyRequest request) {

    }

    @Override
    public MemberAuthenticationInfoGetResponse getMemberAuthenticationInfo(String id) {
        this.getMemberAuthenticationInfo_argument = id;
        return getMemberAuthenticationInfo_returnValue;
    }
}
