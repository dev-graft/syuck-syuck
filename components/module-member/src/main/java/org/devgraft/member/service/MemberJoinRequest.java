package org.devgraft.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberJoinRequest {
    private String nickName;
    private String id;
    private String password;
    private int gender;
}
