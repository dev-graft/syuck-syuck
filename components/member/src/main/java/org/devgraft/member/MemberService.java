package org.devgraft.member;

public interface MemberService {
    boolean alreadyJoin(String identifyToken);
    void join(MemberJoinRequest request);
}
