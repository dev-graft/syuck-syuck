package org.devgraft.auth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthApi {
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Void issueToken() {
        return null;
    }

    @DeleteMapping("{token}")
    public void breakToken(@PathVariable(name = "token") String token) {

    }
}
