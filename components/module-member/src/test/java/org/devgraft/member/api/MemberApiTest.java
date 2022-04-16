package org.devgraft.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.devgraft.member.domain.GenderEnum;
import org.devgraft.member.service.MemberGetResponse;
import org.devgraft.member.service.MemberJoinRequest;
import org.devgraft.member.service.SpyMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberApiTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private SpyMemberService spyMemberService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        spyMemberService = new SpyMemberService();
        mockMvc = MockMvcBuilders.standaloneSetup(new MemberApi(spyMemberService)).build();
    }

    @Test
    void joinMember_createdHttpStatus() throws Exception {
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    void joinMember_returnValue() throws Exception {
        String givenNickName = "nickName";
        String givenId = "id";
        String givenPassword = "password";
        GenderEnum givenGender = GenderEnum.Female;
        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenNickName, givenId, givenPassword, givenGender);

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(givenMemberJoinRequest)))
                .andExpect(jsonPath("$.nickName", equalTo(givenNickName)))
                .andExpect(jsonPath("$.id", equalTo(givenId)))
                .andExpect(jsonPath("$.gender", equalTo(givenGender.name())))
                .andExpect(jsonPath("$.createAt", equalTo("2022-03-25 09:32:00")));
    }

    @Test
    void joinMember_passesMemberJoinRequestToMemberService() throws Exception {
        String givenNickName = "nickName2";
        String givenId = "id";
        String givenPassword = "password";
        GenderEnum givenGender = GenderEnum.Female;

        MemberJoinRequest givenMemberJoinRequest = new MemberJoinRequest(givenNickName, givenId, givenPassword, givenGender);

        mockMvc.perform(post("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(givenMemberJoinRequest)));

        assertThat(spyMemberService.argument_request.getNickName()).isEqualTo(givenNickName);
        assertThat(spyMemberService.argument_request.getId()).isEqualTo(givenId);
        assertThat(spyMemberService.argument_request.getPassword()).isEqualTo(givenPassword);
        assertThat(spyMemberService.argument_request.getGender()).isEqualTo(givenGender);
    }

    @Test
    void searchMember_okHttpStatus() throws Exception {
        mockMvc.perform(get("/members/{id}", "id"))
                .andExpect(status().isOk());
    }

    @Test
    void searchMember_returnValue() throws Exception {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String givenId = "id12";
        String givenNickName = "nickNameaaaa";
        GenderEnum givenGender = GenderEnum.Female;

        LocalDateTime givenCreateAt = LocalDateTime.of(2022, 3, 25, 22, 22, 22);
        LocalDateTime givenUpdateAt = LocalDateTime.of(2023, 2, 2, 22, 22, 22);
        spyMemberService.getMember_returnValue = new MemberGetResponse(givenId, givenNickName, givenGender, givenCreateAt, givenUpdateAt);

        mockMvc.perform(get("/members/{id}", givenId))
                .andExpect(jsonPath("$.id", equalTo(givenId)))
                .andExpect(jsonPath("$.nickName", equalTo(givenNickName)))
                .andExpect(jsonPath("$.gender", equalTo(givenGender.name())))
                .andExpect(jsonPath("$.createAt", equalTo(givenCreateAt.format(dateTimeFormatter))))
                .andExpect(jsonPath("$.updateAt", equalTo(givenUpdateAt.format(dateTimeFormatter))));
    }

    @Test
    void searchMember_passesIdToMemberService() throws Exception {
        mockMvc.perform(get("/members/{id}", "id12"));

        assertThat(spyMemberService.argument_id).isEqualTo("id12");
    }

    @Test
    void updateMember_okHttpStatus() throws Exception {
        mockMvc.perform(patch("/members/{id}", "id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void updateMember_passesNickNameToMemberService() throws Exception {
        String givenId = "id";
        mockMvc.perform(patch("/members/{id}", givenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nickName\": \"nickName\"}"));

        assertThat(spyMemberService.modifyMember_id_argument).isEqualTo("id");
        assertThat(spyMemberService.modifyMember_request_argument.getNickName()).isEqualTo("nickName");
    }
}