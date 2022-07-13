package org.devgraft.member;

import org.devgraft.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberIntegrationTest extends IntegrationTest {

    @DisplayName("자신 프로필 불러오기 테스트")
    @Test
    void getMyProfile() throws Exception {
        mockMvc.perform(get("/api/v1/members/me"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data.email", equalTo("email")))
//                .andExpect(jsonPath("$.data.nickname", equalTo("nickname")))
//                .andExpect(jsonPath("$.data.profileImage", equalTo("profileImage")))
//                .andExpect(jsonPath("$.data.stateMessage", equalTo("stateMessage")))
//                .andDo(print());
    }
}