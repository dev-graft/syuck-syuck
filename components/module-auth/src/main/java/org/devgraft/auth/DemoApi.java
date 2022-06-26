package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.MemberGetResponse;
import org.devgraft.member.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("demo")
@RequiredArgsConstructor
@RestController
public class DemoApi {
    private final MemberService memberService;

    @GetMapping
    public MemberGetResponse getMember(@RequestAttribute(AuthUtil.DATA_SIGN_SYNTAX) String dataSignKey) {
        return memberService.getMember(Long.valueOf(dataSignKey));
    }
}
