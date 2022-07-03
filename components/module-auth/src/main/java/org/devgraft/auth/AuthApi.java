package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("auth/api")
@RestController
public class AuthApi {
    private final AuthService authService;

    @GetMapping("refresh")
    public void refresh(@AuthInfo AuthResult authResult, HttpServletResponse response) {
        AuthResult refresh = authService.refresh(authResult.getAccess(), authResult.getRefresh());
        authService.injectAuthorization(refresh.getAccess(), refresh.getRefresh(), response);
    }

    @DeleteMapping("logout")
    public void logout(HttpServletResponse response) {
        authService.removeAuthorization(response);
    }
}
