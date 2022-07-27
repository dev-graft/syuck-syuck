package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public boolean alreadyJoin(final String identifyToken) {
        return memberRepository.findByIdentifyToken(identifyToken)
                .isPresent();
    }

    @Override
    public void join(final MemberJoinRequest request) {
        Member member = Member.of(request.getEmail(), request.getProfileImage(), request.getNickname(), request.getIdentifyToken(), request.getStateMessage());
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    @Override
    public Long getMemberId(final String identifyToken) {
        return memberRepository.findByIdentifyToken(identifyToken)
                .map(Member::getId)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public MemberGetResponse getMember(final Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);
        return MemberGetResponse.of(member.getEmail(), member.getNickname(), member.getProfileImage(), member.getStateMessage());
    }

    @Override
    public void updateMember(Long memberId, MemberUpdateRequest request) {
        memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new)
                .updateProfile(request.getNickname(), request.getStateMessage());
    }
}