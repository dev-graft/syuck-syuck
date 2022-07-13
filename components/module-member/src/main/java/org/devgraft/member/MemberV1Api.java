package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.Credentials;
import org.devgraft.auth.MemberCredentials;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/members")
@RequiredArgsConstructor
@RestController
public class MemberV1Api {
    private final MemberService memberService;

    @GetMapping("me")
    public MemberGetResponse getMyProfile(@Credentials MemberCredentials credentials) {
        return memberService.getMember(credentials.getMemberId());
    }

    @PatchMapping("me")
    public void updateMyProfile(@Credentials MemberCredentials credentials, @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(credentials.getMemberId(), request);
    }
}
