package org.devgraft.auth.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devgraft.auth.AuthResult;
import org.devgraft.auth.AuthService;
import org.devgraft.auth.AuthUserDetails;
import org.devgraft.auth.MemberCredentials;
import org.devgraft.auth.exception.AuthRefreshTokenExpiredException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<AuthResult> authResultOpt = authService.exportAuthorization(request);
        if (authResultOpt.isPresent()) {
            AuthResult authResult = authResultOpt.get();
            try {
                MemberCredentials verity = authService.verity(authResult.getAccess(), authResult.getRefresh());
                AbstractAuthenticationToken authenticationToken = createAuthenticationToken(verity.getMemberId().toString(), verity.getRole());
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authenticationToken);
            } catch (AuthRefreshTokenExpiredException e) {
                authService.removeAuthorization(response);
                throw e;
            }
        }
        filterChain.doFilter(request, response);
    }

    private AbstractAuthenticationToken createAuthenticationToken(final String dataSignKey, final String role) {
        AuthUserDetails user = new AuthUserDetails(dataSignKey, role);
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
}
