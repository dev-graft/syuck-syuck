package org.devgraft.auth.config.handler;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.AuthUtil;
import org.devgraft.member.MemberService;
import org.devgraft.support.jwt.JwtGenerateRequest;
import org.devgraft.support.jwt.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final AuthUtil authUtil;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Long memberId = memberService.getMemberId(oAuth2User.getName());
        String accessToken = jwtService.generateToken(JwtGenerateRequest.of(memberId.toString(), "ROLE_USER", 600));
        String refreshToken = jwtService.generateToken(JwtGenerateRequest.of(accessToken, "ROLE_USER", 3600 * 30 * 12));
        authUtil.injectAuthorization(accessToken, refreshToken, response);

        response.sendRedirect("http://localhost:8080/auth/success/token?" + AuthUtil.ACCESS_TOKEN_SYNTAX + "=" + accessToken);
//        request.getRequestDispatcher("http://localhost:8080/demo/profile").forward(request, response);
    }
}
