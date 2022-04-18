package org.devgraft.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberModifyRequest {
    @Pattern(regexp = "^(?=^.{2,8}$)(?=.*[a-zA-Z가-힣0-9]).*$")
    private String nickName;
}
