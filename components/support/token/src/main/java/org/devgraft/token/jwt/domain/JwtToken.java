package org.devgraft.token.jwt.domain;

import org.devgraft.token.domain.Token;
import lombok.Getter;

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
