package org.devgraft.token.jwt.provider;

import org.devgraft.token.provider.TokenGenerateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtTokenGenerateRequest extends TokenGenerateRequest {
    private final long accessTokenValidity;
    private final long refreshTokenValidity;
}
