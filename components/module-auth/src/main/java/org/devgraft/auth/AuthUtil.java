package org.devgraft.auth;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthUtil {
    public static final String HEADER_TOKEN_PREFIX = "Bearer "; //RF6750
    public static final String ACCESS_TOKEN_SYNTAX = "Authorization";
    public static final String REFRESH_TOKEN_SYNTAX = "refresh_token";
    public static final String CRYPT_SYNTAX = "crypt";
    public static final String DATA_SIGN_SYNTAX = "data-sign";

    public void injectAuthorization(final String accessToken, final String refreshToken, HttpServletResponse response) {
        Assert.notNull(accessToken, "accessToken must not be null");
        Assert.notNull(refreshToken, "refreshToken must not be null");
        response.addHeader(ACCESS_TOKEN_SYNTAX, accessToken);
        Cookie cookie = new Cookie(REFRESH_TOKEN_SYNTAX, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Optional<AuthorizationToken> exportAuthorization(HttpServletRequest request) {
        String authorization = request.getHeader(ACCESS_TOKEN_SYNTAX);
        Optional<Cookie> cookieOpt = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_SYNTAX))
                .findFirst();

        if (!StringUtils.hasText(authorization) || !authorization.startsWith(HEADER_TOKEN_PREFIX) || cookieOpt.isEmpty()) {
            return Optional.empty();
        }

        String accessToken = authorization.replaceAll(HEADER_TOKEN_PREFIX, "");
        String refreshToken = cookieOpt.get().getValue();

        return Optional.of(AuthorizationToken.of(accessToken, refreshToken));
    }
}
