package org.devgraft.auth.api;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.service.AuthService;
import org.devgraft.auth.service.TokenGenerateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthApi {
    private final AuthService authService;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Mono<TokenIssueResponse> issueToken(@Valid @RequestBody TokenGenerateRequest request) {
        authService.generateToken(request);
        return Mono.just(new TokenIssueResponse("accessToken", "refreshToken"));
    }

    @DeleteMapping("{token}")
    public void breakToken(@PathVariable(name = "token") String token) {

    }
}
