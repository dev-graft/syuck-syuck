package org.devgraft.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    // 한, 영(대소문자), 숫자 혀용
    @Pattern(regexp = "/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/")
    @Size(min = 2, max = 10)
    private String nickName;
    // 영(소/대) 숫자 허용
    @Pattern(regexp = "/^[A-Z|a-z|0-9]+$/")
    @Size(min = 6, max = 20)
    private String id;
    // 영(소/대) / 특수문자, 숫자 허용
    @Size(min = 8, max = 20)
    private String password;
    private int gender;
}
