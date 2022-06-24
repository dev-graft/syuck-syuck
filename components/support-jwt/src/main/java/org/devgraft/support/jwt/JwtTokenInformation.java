package org.devgraft.support.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.support.jwt.TokenInformation;

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
