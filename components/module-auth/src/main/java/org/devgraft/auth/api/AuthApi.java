package org.devgraft.auth.api;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.service.AuthService;
import org.devgraft.auth.service.TokenGenerateRequest;
import org.devgraft.auth.service.TokenGenerateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthApi {
    private final AuthService authService;

    /**
     * 토큰 발급
     */
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Mono<TokenIssueResponse> issueToken(@Valid @RequestBody TokenGenerateRequest request) {
        TokenGenerateResponse tokenGenerateResponse = authService.generateToken(request);
        return Mono.just(new TokenIssueResponse("accessToken", "refreshToken"));
    }

    /**
     * 토큰 폐기
     */
    @DeleteMapping
    public void breakToken(@RequestParam(name = "accessToken") String accessToken, @RequestParam("refreshToken") String refreshToken) {

    }
}
