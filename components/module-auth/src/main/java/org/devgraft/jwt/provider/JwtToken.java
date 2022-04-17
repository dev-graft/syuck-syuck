package org.devgraft.jwt.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtToken {
    private final String accessToken;
    private final String refreshToken;
}
