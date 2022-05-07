package org.devgraft.auth.client;

import org.devgraft.client.member.MemberClient;
import org.devgraft.client.member.MemberGetResponse;

public class SpyMemberClient implements MemberClient {
    public String getMember_argument;
    public MemberGetResponse getMember_returnValue = new MemberGetResponse("id", "password!", "name!");
    @Override
    public MemberGetResponse getMember(String id) {
        this.getMember_argument = id;
        return getMember_returnValue;
    }
}
