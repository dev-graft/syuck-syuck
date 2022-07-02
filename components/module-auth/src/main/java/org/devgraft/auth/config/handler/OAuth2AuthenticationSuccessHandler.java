package org.devgraft.auth.config.handler;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.AuthUtil;
import org.devgraft.auth.JwtAuthResult;
import org.devgraft.auth.JwtAuthService;
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
    private final JwtAuthService jwtAuthService;
    private final AuthUtil authUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        JwtAuthResult jwtAuthResult = jwtAuthService.issue(oAuth2User.getName());
        authUtil.injectAuthorization(jwtAuthResult.getAccess(), jwtAuthResult.getRefresh(), response);
        response.sendRedirect("http://localhost:8080/auth/success/token?" + AuthUtil.ACCESS_TOKEN_SYNTAX + "=" + jwtAuthResult.getAccess());
//        request.getRequestDispatcher("http://localhost:8080/demo/profile").forward(request, response);
    }
}
