package org.devgraft.member.service;

import org.devgraft.member.MemberFixture;
import org.devgraft.member.domain.GenderEnum;
import org.devgraft.member.domain.Member;
import org.devgraft.member.domain.SpyMemberRepository;
import org.devgraft.member.exception.ExistMemberException;
import org.devgraft.member.exception.NotFoundMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
    void joinMember_throwExistMemberException() {
        Member givenMember = MemberFixture.anMember().build();
        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenMember.getNickName(), givenMember.getId(), givenMember.getPassword(), givenMember.getGender());
        spyMemberRepository.existsById_returnValue = true;

        Assertions.assertThrows(ExistMemberException.class, () -> memberService.joinMember(givenMemberJoinRequest));
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
        String givenNickName = "nickName2";
        Member givenMember = MemberFixture.anMember().build();
        spyMemberRepository.findById_returnValue = Optional.of(givenMember);

        memberService.modifyMember(givenId, new MemberModifyRequest(givenNickName));

        assertThat(spyMemberRepository.findById_argument).isEqualTo(givenMember.getId());
        assertThat(spyMemberRepository.save_argument.getNickName()).isEqualTo(givenNickName);
    }

    @Test
    void modifyMember_throwRuntimeException() {
        Assertions.assertThrows(RuntimeException.class, () ->
                memberService.modifyMember("id", new MemberModifyRequest("nickName")));
    }

    @Test
    void getAuthenticationInfo_returnValue() throws Exception {
        spyMemberRepository.findById_returnValue = Optional.of(MemberFixture.anMember().build());

        MemberAuthenticationInfoGetResponse response = memberService.getAuthenticationInfo("id");

        assertThat(response.getId()).isEqualTo("id");
        assertThat(response.getPassword()).isEqualTo("password");
        assertThat(response.getNickName()).isEqualTo("nickName");
        assertThat(response.getGender()).isEqualTo(GenderEnum.Female);
    }

    @Test
    void getAuthenticationInfo_passesIdToRepository() throws Exception {
        spyMemberRepository.findById_returnValue = Optional.of(MemberFixture.anMember().build());
        String givenId = "id";

        memberService.getAuthenticationInfo(givenId);

        assertThat(spyMemberRepository.findById_argument).isEqualTo(givenId);
    }

    @Test
    void getAuthenticationInfo_throwNotFoundMemberException() throws Exception {

        Assertions.assertThrows(NotFoundMemberException.class, () ->
                memberService.getAuthenticationInfo("givenId"));

    }
}