package org.devgraft.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.devgraft.auth.exception.AuthAccessTokenExpiredException;
import org.devgraft.auth.exception.AuthRefreshTokenExpiredException;
import org.devgraft.auth.exception.UnverifiedAuthRequestException;
import org.devgraft.member.MemberService;
import org.devgraft.support.jwt.JwtGenerateRequest;
import org.devgraft.support.jwt.JwtService;
import org.devgraft.support.provider.SHA256Provider;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final MemberService memberService;
    private final JwtService jwtService;
    private final SHA256Provider sha256Provider;

    @Override
    public AuthResult issuedMemberAuthToken(final String identifyToken) {
        Long memberId = memberService.getMemberId(identifyToken);
        return issuedMemberAuthToken(memberId);
    }

    @Override
    public AuthResult refresh(final String accessToken, final String refresh) {
        if (isIllegalAuthRequest(accessToken, refresh)) throw new UnverifiedAuthRequestException();
        return issuedMemberAuthToken(Long.valueOf(jwtService.getBody(refresh).getAudience()));
    }

    @Override
    public void injectAuthorization(final String accessToken, final String refreshToken, HttpServletResponse response) {
        Assert.notNull(accessToken, "accessToken must not be null");
        Assert.notNull(refreshToken, "refreshToken must not be null");
        response.addHeader(TokenName.ACCESS_TOKEN_SYNTAX, accessToken);
        Cookie cookie = new Cookie(TokenName.REFRESH_TOKEN_SYNTAX, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public Optional<AuthResult> exportAuthorization(HttpServletRequest request) {
        String authorization = request.getHeader(TokenName.ACCESS_TOKEN_SYNTAX);
        Optional<Cookie> cookieOpt = Arrays.stream(request.getCookies() != null ? request.getCookies() : new Cookie[]{})
                .filter(cookie -> cookie.getName().equals(TokenName.REFRESH_TOKEN_SYNTAX))
                .findFirst();

        if (!StringUtils.hasText(authorization) || !authorization.startsWith(TokenName.HEADER_TOKEN_PREFIX) || cookieOpt.isEmpty()) {
            return Optional.empty();
        }

        String accessToken = authorization.replaceAll(TokenName.HEADER_TOKEN_PREFIX, "");
        String refreshToken = cookieOpt.get().getValue();

        return Optional.of(AuthResult.of(accessToken, refreshToken));
    }

    @Override
    public void removeAuthorization(HttpServletResponse response) {
        Cookie cookie = new Cookie(TokenName.REFRESH_TOKEN_SYNTAX, null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Override
    public MemberCredentials verity(String accessToken, String refresh) throws AuthAccessTokenExpiredException, AuthRefreshTokenExpiredException, UnverifiedAuthRequestException {
        if (!jwtService.verifyToken(accessToken)) throw new AuthAccessTokenExpiredException();
        if (!jwtService.verifyToken(refresh)) throw new AuthRefreshTokenExpiredException();
        if (isIllegalAuthRequest(accessToken, refresh)) throw new UnverifiedAuthRequestException();
        Claims accessTokenBody = jwtService.getBody(accessToken);
        return MemberCredentials.of(Long.valueOf(accessTokenBody.getAudience()), (String)accessTokenBody.get("role"));
    }

    private String encrypt(final String text) {
        return sha256Provider.encrypt(text, "CRYPT_LOGIN_TOKEN");
    }

    private boolean isIllegalAuthRequest(final String accessToken, final String refresh) {
        Claims refreshTokenBody = jwtService.getBody(refresh);
        return !Objects.equals(refreshTokenBody.getSubject(), encrypt(accessToken));
    }

    private AuthResult issuedMemberAuthToken(Long memberId) {
        String access = jwtService.generateToken(JwtGenerateRequest.of("LOGIN", memberId.toString(), "ROLE_USER", 600));
        String refresh = jwtService.generateToken(JwtGenerateRequest.of(encrypt(access), memberId.toString(), "ROLE_USER", 3600 * 30 * 12));
        return AuthResult.of(access, refresh);
    }
}
