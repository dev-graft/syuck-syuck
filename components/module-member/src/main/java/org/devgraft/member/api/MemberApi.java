package org.devgraft.member.api;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.service.MemberGetResponse;
import org.devgraft.member.service.MemberJoinRequest;
import org.devgraft.member.service.MemberJoinResponse;
import org.devgraft.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MemberJoinResponse joinMember(@RequestBody MemberJoinRequest request) {
        return memberService.joinMember(request);
    }

    @GetMapping("{id}")
    public MemberGetResponse searchMember(@PathVariable(name = "id") String id) {
        return memberService.getMember(id);
    }
}