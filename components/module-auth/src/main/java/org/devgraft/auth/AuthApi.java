package org.devgraft.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("auth")
@Controller
public class AuthApi {
    @GetMapping
    public void login() {

    }

    @GetMapping("success/token")
    public String authSuccess(@RequestParam(AuthUtil.ACCESS_TOKEN_SYNTAX) String accessToken, HttpServletResponse response, Model model) {
        response.addHeader(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        model.addAttribute(AuthUtil.ACCESS_TOKEN_SYNTAX, accessToken);
        return "token";
    }
}
