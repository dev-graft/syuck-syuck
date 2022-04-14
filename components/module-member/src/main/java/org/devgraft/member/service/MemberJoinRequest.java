package org.devgraft.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 *
 * 정규식 특수문자
 * / 정규식 시작
 * ^ 문자열 시작
 * [] 범위
 * A-Z A~Z
 * | or
 * + 앞의 조건이 한번 이상 호출되었는지
 * $ 문자열 끝
 * / 정규식 끝
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {
    @Pattern(regexp = "^(?=^.{2,8}$)(?=.*[a-zA-Z가-힣0-9]).*$")
    private String nickName;
    @Pattern(regexp = "^(?=^.{6,20}$)(?=.*[a-zA-Z0-9]).*$")
    private String id;
    @Pattern(regexp = "^(?=^.{8,20}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*+=.,\\\\\\-<>/;:'~₩]).*$")
    private String password;
    private int gender;
}
