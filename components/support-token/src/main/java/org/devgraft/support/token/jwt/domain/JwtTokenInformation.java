package org.devgraft.support.token.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.support.token.domain.TokenInformation;

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
