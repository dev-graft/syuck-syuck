package org.devgraft.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MemberGetResponse {
    private final String email;
    private final String nickName;
    private final String profileImage;
    private final String stateMessage;
}
