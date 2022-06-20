package org.devgraft.support.token.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.support.token.TokenGenerateRequest;

@AllArgsConstructor
@Getter
public class JwtTokenGenerateRequest extends TokenGenerateRequest {
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
}
