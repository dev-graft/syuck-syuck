package org.devgraft.auth.oauth.handler;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.AuthResult;
import org.devgraft.auth.AuthService;
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
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        AuthResult authResult = authService.issuedMemberAuthToken(oAuth2User.getName());
        request.getSession().setAttribute("refresh", authResult.getRefresh());
        response.sendRedirect("http://localhost:8080/auth/success?token=" + authResult.getAccess());
    }
}
