package org.devgraft.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.client.member.GenderEnum;

@AllArgsConstructor
@Getter
public class MemberAuthenticationResponse {
    private final String id;
    private final String nickName;
    private final GenderEnum gender;
}
