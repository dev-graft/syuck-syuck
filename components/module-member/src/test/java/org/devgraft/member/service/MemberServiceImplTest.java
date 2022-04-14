package org.devgraft.member.service;

import org.devgraft.member.MemberFixture;
import org.devgraft.member.domain.Member;
import org.devgraft.member.domain.SpyMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {
    private MemberServiceImpl memberService;
    private SpyMemberRepository spyMemberRepository;

    @BeforeEach
    void setUp() {
        spyMemberRepository = new SpyMemberRepository();
        memberService = new MemberServiceImpl(spyMemberRepository);
    }

    @Test
    void joinMember_returnValue() {
        Member givenMember = MemberFixture.anMember().build();
        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenMember.getNickName(), givenMember.getId(), null, givenMember.getGender());
        spyMemberRepository.save_returnValue = givenMember;

        MemberJoinResponse memberJoinResponse = memberService.joinMember(givenMemberJoinRequest);

        assertThat(memberJoinResponse.getId()).isEqualTo(givenMember.getId());
        assertThat(memberJoinResponse.getNickName()).isEqualTo(givenMember.getNickName());
        assertThat(memberJoinResponse.getGender()).isEqualTo(givenMember.getGender());
        assertThat(memberJoinResponse.getCreateAt()).isEqualTo(givenMember.getCreateAt());
    }

    @Test
    void joinMember_passesMemberToMemberRepository() {
        Member givenMember = MemberFixture.anMember().build();
        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenMember.getNickName(), givenMember.getId(), givenMember.getPassword(), givenMember.getGender());

        memberService.joinMember(givenMemberJoinRequest);

        assertThat(spyMemberRepository.save_argument.getId()).isEqualTo(givenMemberJoinRequest.getId());
        assertThat(spyMemberRepository.save_argument.getPassword()).isEqualTo(givenMemberJoinRequest.getPassword());
        assertThat(spyMemberRepository.save_argument.getNickName()).isEqualTo(givenMemberJoinRequest.getNickName());
        assertThat(spyMemberRepository.save_argument.getGender()).isEqualTo(givenMemberJoinRequest.getGender());
    }

    @Test
    void joinMember_throwRuntimeExceptionBySaveOfRepository() {
        Member givenMember = MemberFixture.anMember().build();
        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenMember.getNickName(), givenMember.getId(), givenMember.getPassword(), givenMember.getGender());
        spyMemberRepository.existsById_returnValue = true;

        Assertions.assertThrows(RuntimeException.class, () -> memberService.joinMember(givenMemberJoinRequest));
    }

    @Test
    void getMember_passesIdToMemberRepository() {
        String givenId = "id";
        spyMemberRepository.findById_returnValue = Optional.of(MemberFixture.anMember().build());

        memberService.getMember(givenId);

        assertThat(spyMemberRepository.findById_argument).isEqualTo(givenId);
    }

    @Test
    void getMember_returnValue() {
        Member givenMember = MemberFixture.anMember().build();
        spyMemberRepository.findById_returnValue = Optional.of(givenMember);

        MemberGetResponse response = memberService.getMember(null);

        assertThat(response.getId()).isEqualTo(givenMember.getId());
        assertThat(response.getNickName()).isEqualTo(givenMember.getNickName());
        assertThat(response.getGender()).isEqualTo(givenMember.getGender());
        assertThat(response.getCreateAt()).isEqualTo(givenMember.getCreateAt());
        assertThat(response.getUpdateAt()).isEqualTo(givenMember.getUpdateAt());
    }

    @Test
    void getMember_throwRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () -> memberService.getMember(null));
    }

    @Test
    void modifyMember_passesIdMemberRepository() {
        String givenId = "id";
        Member givenMember = MemberFixture.anMember().build();
        spyMemberRepository.findById_returnValue = Optional.of(givenMember);

        memberService.modifyMember(givenId, new MemberModifyRequest("nickName"));

        assertThat(spyMemberRepository.findById_argument).isEqualTo(givenMember.getId());
    }

    @Test
    void modifyMember_throwRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                memberService.modifyMember("id", new MemberModifyRequest("nickName")));
    }
}