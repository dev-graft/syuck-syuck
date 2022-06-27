package org.devgraft.demo;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.MemberJoinRequest;
import org.devgraft.member.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @PostMapping
    public void joinMember(@RequestBody MemberJoinRequest request) {
//        memberService.join(request);
        System.out.println("");
    }
}
