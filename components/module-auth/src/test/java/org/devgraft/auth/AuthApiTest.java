package org.devgraft.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthApiTest {
    private MockMvc mockMvc;
    private SpyAuthService spyAuthService;

    @BeforeEach
    void setUp() {
        spyAuthService = new SpyAuthService();
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthApi(spyAuthService))
                .setCustomArgumentResolvers(new AuthInfoResolver(spyAuthService))
                .build();
    }

    @DisplayName("인증정보 갱신 Api 테스트")
    @Test
    void refresh_Api_Test() throws Exception {
        spyAuthService.exportAuthorization_returnValue = Optional.of(AuthResult.of("access", "refresh"));
        spyAuthService.refresh_returnValue = AuthResult.of("accessR", "refreshR");

        MockHttpServletResponse response = mockMvc.perform(get("/auth/api/refresh"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        assertThat(response.getHeader("access")).isEqualTo("accessR");
        assertThat(response.getHeader("refresh")).isEqualTo("refreshR");
    }

    @DisplayName("로그아웃 Api Test")
    @Test
    void logout_Api_Test() throws Exception {
        mockMvc.perform(delete("/auth/api/logout"))
                .andExpect(status().isOk());

        assertThat(spyAuthService.removeAuthorization_wasCall).isTrue();
    }
}