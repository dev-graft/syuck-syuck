package org.devgraft.auth;

import org.devgraft.auth.exception.AuthAccessTokenExpiredException;
import org.devgraft.auth.exception.AuthRefreshTokenExpiredException;
import org.devgraft.auth.exception.UnverifiedAuthRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SpyAuthService implements AuthService {
    public Optional<AuthResult> exportAuthorization_returnValue = Optional.empty();
    public String refresh_argument_accessToken;
    public String refresh_argument_refresh;
    public AuthResult refresh_returnValue;
    public boolean removeAuthorization_wasCall = false;
    @Override
    public AuthResult issuedMemberAuthToken(String identifyToken) {
        return null;
    }

    @Override
    public AuthResult refresh(String accessToken, String refresh) {
        this.refresh_argument_accessToken = accessToken;
        this.refresh_argument_refresh = refresh;

        return refresh_returnValue;
    }

    @Override
    public void injectAuthorization(String accessToken, String refreshToken, HttpServletResponse response) {
        response.addHeader("access", accessToken);
        response.addHeader("refresh", refreshToken);
    }

    @Override
    public Optional<AuthResult> exportAuthorization(HttpServletRequest request) {
        return exportAuthorization_returnValue;
    }

    @Override
    public void removeAuthorization(HttpServletResponse response) {
        this.removeAuthorization_wasCall = true;
    }

    @Override
    public MemberCredentials verity(String accessToken, String refresh) throws AuthAccessTokenExpiredException, AuthRefreshTokenExpiredException, UnverifiedAuthRequestException {
        return null;
    }
}
