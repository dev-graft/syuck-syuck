package org.devgraft.member;

import org.devgraft.auth.AuthResult;
import org.devgraft.auth.CredentialsResolver;
import org.devgraft.auth.MemberCredentials;
import org.devgraft.auth.SpyAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberV1ApiTest {
    private SpyMemberService spyMemberService;
    private SpyAuthService spyAuthService;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        spyMemberService = new SpyMemberService();
        spyAuthService = new SpyAuthService();
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberV1Api(spyMemberService))
                .setCustomArgumentResolvers(new CredentialsResolver(spyAuthService))
                .build();
    }

    @DisplayName("자신의 프로필 정보 갖고오기")
    @Test
    void getMyProfile_Api_Test() throws Exception {
        spyAuthService.exportAuthorization_returnValue = Optional.of(AuthResult.of("access", "refresh"));
        spyAuthService.verity_returnValue = MemberCredentials.of(2L, "USER");
        spyMemberService.getMember_returnValue = MemberGetResponse.of("email", "nickname", "profileImage", "stateMessage");

        mockMvc.perform(get("/api/v1/members/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo("email")))
                .andExpect(jsonPath("$.nickname", equalTo("nickname")))
                .andExpect(jsonPath("$.profileImage", equalTo("profileImage")))
                .andExpect(jsonPath("$.stateMessage", equalTo("stateMessage")))
        ;
    }
}