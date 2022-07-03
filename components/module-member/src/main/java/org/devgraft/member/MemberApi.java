package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.Credentials;
import org.devgraft.auth.MemberCredentials;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("api/v1/members/me")
    public MemberGetResponse getMyProfile(@Credentials MemberCredentials credentials) {
        return memberService.getMember(credentials.getMemberId());
    }
}
