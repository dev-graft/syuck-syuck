package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
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
    private final AuthService authService;
    @GetMapping("refresh")
    public void refresh(@AuthInfo AuthResult authResult, HttpServletResponse response) {
        AuthResult refresh = authService.refresh(authResult.getAccess(), authResult.getRefresh());
        authService.injectAuthorization(refresh.getAccess(), refresh.getRefresh(), response);
    }

    @GetMapping("success")
    public String authSuccess(@RequestParam String token, HttpServletRequest request, HttpServletResponse response, Model model) {
//        response.addHeader(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
//        Optional<Cookie> cookieOpt = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[]{})
//                .filter(cookie -> cookie.getName().equals(AuthUtil.REFRESH_TOKEN_SYNTAX))
//                .findFirst();
//        response.addCookie(cookieOpt.get());
//        model.addAttribute(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        return "token";
    }
}
