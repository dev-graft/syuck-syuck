package org.devgraft.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @GetMapping("api/v1/members/me")
    public MemberGetResponse getMyProfile(@RequestAttribute("data-sign") Long memberId) {
        return memberService.getMember(memberId);
    }
}
