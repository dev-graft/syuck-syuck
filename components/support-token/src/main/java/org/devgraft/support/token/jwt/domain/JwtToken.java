package org.devgraft.support.token.jwt.domain;

import lombok.Getter;
import org.devgraft.support.token.domain.Token;

@Getter
public class JwtToken extends Token<JwtTokenInformation> {
    public JwtToken(JwtTokenInformation information) {
        super(information);
    }

    public String accessToken() {
        return getInformation().getAccessToken();
    }

    public String refreshToken() {
        return getInformation().getRefreshToken();
    }
}
