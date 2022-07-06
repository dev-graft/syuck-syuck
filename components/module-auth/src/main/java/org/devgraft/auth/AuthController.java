package org.devgraft.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("auth")
@Controller
public class AuthController {
    private final AuthService authService;

    @GetMapping("success")
    public String authSuccess(@RequestParam(name = "token") String token, HttpServletResponse response, HttpSession session, Model model) {
        String refresh = (String)session.getAttribute("refresh");
        session.removeAttribute("refresh");
        authService.injectAuthorization(token, refresh, response);
        model.addAttribute(TokenName.ACCESS_TOKEN_SYNTAX, token);
        return "token";
    }
}
