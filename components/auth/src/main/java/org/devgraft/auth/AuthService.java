package org.devgraft.auth;

public interface AuthService {
    AuthResult refresh(final String accessToken, final String refresh); // 인가 토큰 재발급
    AuthResult issuedToken(final String accessToken, final String refresh); // 인가 정보 토큰 발급
    void injectAuthorization(); // 인가 정보 주입
    void exportAuthorization(); // 인가 정보 추출
}
