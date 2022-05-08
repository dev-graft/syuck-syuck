package org.devgraft.member.service;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.domain.GenderEnum;
import org.devgraft.member.domain.Member;
import org.devgraft.member.domain.MemberRepository;
import org.devgraft.member.exception.ExistMemberException;
import org.devgraft.member.exception.NotFoundMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public MemberJoinResponse joinMember(MemberJoinRequest request) {
        if (memberRepository.existsById(request.getId())) {
            throw new ExistMemberException();
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

    @Override
    public MemberAuthenticationInfoGetResponse getAuthenticationInfo(String id) {
        Member member = memberRepository.findById("id").orElseThrow(NotFoundMemberException::new);
        return new MemberAuthenticationInfoGetResponse(member);
    }
}