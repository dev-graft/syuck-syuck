package org.devgraft.auth.config.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devgraft.auth.AuthUserDetails;
import org.devgraft.auth.AuthUtil;
import org.devgraft.auth.AuthorizationToken;
import org.devgraft.support.jwt.JwtService;
import org.devgraft.support.provider.SHA256Provider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final SHA256Provider sha256Provider;
    private final JwtService jwtService;
    private final AuthUtil authUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<AuthorizationToken> authorizationTokenOpt = authUtil.exportAuthorization(request);
        if (authorizationTokenOpt.isPresent()) {
            AuthorizationToken authorizationToken = authorizationTokenOpt.get();
            if (!jwtService.verifyToken(authorizationToken.getAccessToken())) throw new JwtAuthorizationException("인증 정보가 만료되었습니다. 갱신요청을 진행해주세요.", HttpStatus.UNAUTHORIZED);
            if (!jwtService.verifyToken(authorizationToken.getRefreshToken())) {
                deleteCookie(response);
                throw new JwtAuthorizationException("인증 정보가 만료되었습니다. 로그인 요청을 진행해주세요.", HttpStatus.UNAUTHORIZED);
            }
            Claims accessTokenBody = jwtService.getBody(authorizationToken.getAccessToken());
            Claims refreshTokenBody = jwtService.getBody(authorizationToken.getRefreshToken());
            if(!Objects.equals(sha256Provider.encrypt(authorizationToken.getAccessToken(), "CRYPT_LOGIN_TOKEN"), refreshTokenBody.getSubject())) throw new JwtAuthorizationException("검증되지 않은 인증요청입니다.", HttpStatus.UNAUTHORIZED);
            AbstractAuthenticationToken authenticationToken = createAuthenticationToken(accessTokenBody.getAudience(), (String) accessTokenBody.get("role"));
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authenticationToken);
            request.setAttribute(AuthUtil.DATA_SIGN_SYNTAX, accessTokenBody.getAudience());
        }
        filterChain.doFilter(request, response);
    }

    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AuthUtil.REFRESH_TOKEN_SYNTAX, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private AbstractAuthenticationToken createAuthenticationToken(final String dataSignKey, final String role) {
        AuthUserDetails user = new AuthUserDetails(dataSignKey, role);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
