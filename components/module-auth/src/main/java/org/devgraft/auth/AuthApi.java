package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("auth")
@Controller
public class AuthApi {
    private final JwtAuthService jwtAuthService;
    private final AuthUtil authUtil;
    @GetMapping("refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthorizationToken authorizationToken = authUtil.exportAuthorization(request).orElseThrow(RuntimeException::new);
        JwtAuthResult jwtAuthResult = jwtAuthService.refresh(authorizationToken.getAccessToken(), authorizationToken.getRefreshToken());
        authUtil.injectAuthorization(jwtAuthResult.getAccess(), jwtAuthResult.getRefresh(), response);
    }

    @GetMapping("success/token")
    public String authSuccess(@RequestParam(AuthUtil.ACCESS_TOKEN_SYNTAX) String accessToken, HttpServletRequest request, HttpServletResponse response, Model model) {
        response.addHeader(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        Optional<Cookie> cookieOpt = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[]{})
                .filter(cookie -> cookie.getName().equals(AuthUtil.REFRESH_TOKEN_SYNTAX))
                .findFirst();
        response.addCookie(cookieOpt.get());
        model.addAttribute(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        return "token";
    }
}
