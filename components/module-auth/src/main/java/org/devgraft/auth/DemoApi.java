package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.devgraft.member.MemberGetResponse;
import org.devgraft.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("demo")
@RequiredArgsConstructor
@Controller
public class DemoApi {
    private final MemberService memberService;

//    @GetMapping
//    public MemberGetResponse getMember(@RequestAttribute(AuthUtil.DATA_SIGN_SYNTAX) String dataSignKey) {
//        return memberService.getMember(Long.valueOf(dataSignKey));
//    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("login/token")
    public String login(@RequestParam(AuthUtil.ACCESS_TOKEN_SYNTAX) String accessToken, HttpServletResponse response, Model model) {
        response.addHeader(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        model.addAttribute(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        return "token";
    }

    @GetMapping("profile")
    public String getProfile(@RequestAttribute(AuthUtil.DATA_SIGN_SYNTAX) String dataSignKey, Model model) {
        MemberGetResponse member = memberService.getMember(Long.valueOf(dataSignKey));
        model.addAttribute("member", member);
        return "profile";
    }
}
