package org.devgraft.client.member;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "member-client", url = "${client.member.url}")
public interface MemberClient {
    @PostMapping
    MemberJoinResponse joinMember(@RequestBody MemberJoinRequest request);
    @GetMapping("{id}")
    MemberGetResponse searchMember(@PathVariable(name = "id") String id);

    @PatchMapping("{id}")
    void updateMember(@PathVariable(name = "id") String id, @RequestBody MemberModifyRequest request);

    @GetMapping("auth/{id}")
    MemberAuthenticationInfoGetResponse getMemberAuthenticationInfo(@PathVariable(name = "id") String id);
}
