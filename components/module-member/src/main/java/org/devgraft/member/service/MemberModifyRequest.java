package org.devgraft.member.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberModifyRequest {
    private String id;
    private String nickName;
}
