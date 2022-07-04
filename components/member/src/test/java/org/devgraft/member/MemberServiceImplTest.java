package org.devgraft.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberServiceImplTest {
    SpyMemberRepository spyMemberRepository;
    MemberService memberService;

    @BeforeEach
    void setUp() {
        spyMemberRepository = new SpyMemberRepository();
        memberService = new MemberServiceImpl(spyMemberRepository);
    }

    @DisplayName("맴버 아이디 조회/결과")
    @Test
    void getMemberId_returnValue() {
        Long result = memberService.getMemberId("identifyToken");

        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("가입여부/결과")
    @Test
    void alreadyJoin_returnValue() {
        String givenIdentifyToken = "identifyToken";

        boolean result = memberService.alreadyJoin(givenIdentifyToken);

        assertThat(result).isTrue();
    }

    @DisplayName("가입여부/패스")
    @Test
    void alreadyJoin_passesIdentifyTokenToRepository() {
        String givenIdentifyToken = "identifyToken";

        memberService.alreadyJoin(givenIdentifyToken);

        assertThat(spyMemberRepository.findByIdentifyToken_identifyToken_argument).isEqualTo(givenIdentifyToken);
    }

    @DisplayName("가입/패스")
    @Test
    void join_passesMemberToRepository() {
        MemberJoinRequest givenMemberJoinRequest = MemberJoinRequest.of("email", "profileImage", "nickName", "identifyToken", "stateMessage");

        memberService.join(givenMemberJoinRequest);

        assertThat(spyMemberRepository.save_member_argument).isNotNull();
        assertThat(spyMemberRepository.save_member_argument.getEmail()).isEqualTo(givenMemberJoinRequest.getEmail());
        assertThat(spyMemberRepository.save_member_argument.getProfileImage()).isEqualTo(givenMemberJoinRequest.getProfileImage());
        assertThat(spyMemberRepository.save_member_argument.getNickname()).isEqualTo(givenMemberJoinRequest.getNickName());
        assertThat(spyMemberRepository.save_member_argument.getIdentifyToken()).isEqualTo(givenMemberJoinRequest.getIdentifyToken());
        assertThat(spyMemberRepository.save_member_argument.getStateMessage()).isEqualTo(givenMemberJoinRequest.getStateMessage());
    }

    @DisplayName("회원 조회/결과")
    @Test
    void getMember_returnValue() {
        long givenId = 1L;
        String givenEmail = "A_Email";
        String givenNickName = "A_NickName";
        String givenProfileImage = "A_ProfileImage";
        String givenStateMessage = "A_StateMessage";
        spyMemberRepository.optionalMember = Optional.of(MemberFixture.anMember()
                .id(givenId)
                .email(givenEmail)
                .nickName(givenNickName)
                .profileImage(givenProfileImage)
                .stateMessage(givenStateMessage)
                .build());

        MemberGetResponse result = memberService.getMember(givenId);

        assertThat(result.getEmail()).isEqualTo(givenEmail);
        assertThat(result.getNickName()).isEqualTo(givenNickName);
        assertThat(result.getProfileImage()).isEqualTo(givenProfileImage);
        assertThat(result.getStateMessage()).isEqualTo(givenStateMessage);
    }
}
