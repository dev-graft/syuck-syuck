package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.Credentials;
import org.devgraft.auth.MemberCredentials;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("/v1/members/me")
    public MemberGetResponse getMyProfile(@Credentials MemberCredentials credentials) {
        return memberService.getMember(credentials.getMemberId());
    }
}
