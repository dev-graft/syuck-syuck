package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.devgraft.support.jwt.JwtService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("auth")
@Controller
public class AuthApi {
    private final JwtService jwtService;
    private final AuthUtil authUtil;
    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthorizationToken authorizationToken = authUtil.exportAuthorization(request).orElseThrow(RuntimeException::new);
        // access, refresh를 제출하면 내부 내용 확인
        // 정상적인 요청일 경우 결과 토큰 정보 기반 재발급 후 반환
        // 즉 재발급하려면 accessToken의 경우 만료되더라도 내용을 확인해야함
    }

    @GetMapping("success/token")
    public String authSuccess(@RequestParam(AuthUtil.ACCESS_TOKEN_SYNTAX) String accessToken, HttpServletResponse response, Model model) {
        response.addHeader(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        model.addAttribute(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        return "token";
    }
}
