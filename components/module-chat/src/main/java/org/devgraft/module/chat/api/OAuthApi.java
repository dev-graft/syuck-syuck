package org.devgraft.module.chat.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("social/login")
@RestController
public class OAuthApi {

    @GetMapping
    public void login() {

    }
}
