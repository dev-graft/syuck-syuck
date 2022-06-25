package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public boolean alreadyJoin(String identifyToken) {
        return memberRepository.findByIdentifyToken(identifyToken)
                .isPresent();
    }

    @Override
    public void join(MemberJoinRequest request) {
        Member member = Member.of(request.getProfileImage(), request.getNickName(), request.getIdentifyToken(), request.getStateMessage());
        memberRepository.save(member);
    }
}