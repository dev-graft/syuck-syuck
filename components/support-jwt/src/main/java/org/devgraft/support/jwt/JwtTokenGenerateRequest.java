package org.devgraft.support.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokenGenerateRequest extends TokenGenerateRequest {
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
}

