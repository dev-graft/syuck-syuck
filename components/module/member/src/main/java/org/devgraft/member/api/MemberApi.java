package org.devgraft.member.api;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.domain.GenderEnum;
import org.devgraft.member.service.MemberAuthenticationInfoGetResponse;
import org.devgraft.member.service.MemberGetResponse;
import org.devgraft.member.service.MemberJoinRequest;
import org.devgraft.member.service.MemberJoinResponse;
import org.devgraft.member.service.MemberService;
import org.devgraft.member.service.MemberModifyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("members")
@RequiredArgsConstructor
@RestController
public class MemberApi {
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public MemberJoinResponse joinMember(@RequestBody MemberJoinRequest request) {
        return memberService.joinMember(request);
    }

    /**
     * 회원 조회
     */
    @GetMapping("{id}")
    public MemberGetResponse searchMember(@PathVariable(name = "id") String id) {
        return memberService.getMember(id);
    }

    /**
     * 회원 정보 업데이트
     */
    @PatchMapping("{id}")
    public void updateMember(@PathVariable(name = "id") String id, @RequestBody MemberModifyRequest request) {
        memberService.modifyMember(id, request);
    }

    @GetMapping("auth/{id}")
    public MemberAuthenticationInfoGetResponse getMemberAuthenticationInfo(@PathVariable(name = "id") String id) {
        return memberService.getAuthenticationInfo(id);
    }
}