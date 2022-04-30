package org.devgraft.token.jwt.domain;

import org.devgraft.token.domain.TokenInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class JwtTokenInformation extends TokenInformation {
    private final String accessToken;
    private final String refreshToken;
    private final Long accessTokenValidity;
    private final Long refreshTokenValidity;
    private final Date createAt;
}
