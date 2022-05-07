package org.devgraft.client.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {
    @Pattern(regexp = "^(?=^.{2,8}$)(?=.*[a-zA-Z가-힣0-9]).*$")
    private String nickName;
    @Pattern(regexp = "^(?=^.{6,20}$)(?=.*[a-zA-Z0-9]).*$")
    private String id;
    @Pattern(regexp = "^(?=^.{8,20}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).*$")
    private String password;
    private GenderEnum gender;
}

