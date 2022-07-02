package org.devgraft.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public interface AuthService {
    AuthResult issuedMemberAuthToken(final String identifyToken); // 인가 정보 토큰 발급
    AuthResult refresh(final String accessToken, final String refresh); // 인가 토큰 재발급
    void injectAuthorization(final String accessToken, final String refreshToken, HttpServletResponse response); // 인가 정보 주입
    Optional<AuthResult> exportAuthorization(HttpServletRequest request); // 인가 정보 추출
    void removeAuthorization(HttpServletResponse response);
}
