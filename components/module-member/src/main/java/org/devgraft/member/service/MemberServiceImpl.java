package org.devgraft.member.service;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.domain.Member;
import org.devgraft.member.domain.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberJoinResponse joinMember(MemberJoinRequest request) {
        if (memberRepository.existsById(request.getId())) {
            throw new RuntimeException();
        }

        Member member = memberRepository.save(Member.create(
                request.getId(),
                request.getPassword(),
                request.getNickName(),
                request.getGender()));

        return new MemberJoinResponse(member.getNickName(), member.getId(), member.getGender(), member.getCreateAt());
    }

    @Override
    public MemberGetResponse getMember(String id) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        return new MemberGetResponse(member.getId(), member.getNickName(), member.getGender(), member.getCreateAt(), member.getUpdateAt());
    }

    @Transactional
    @Override
    public void modifyMember(String id, MemberModifyRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        member.update(request.getNickName());
        memberRepository.save(member);
    }
}