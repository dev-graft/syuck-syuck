package org.devgraft.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.devgraft.member.MemberService;
import org.devgraft.support.jwt.JwtGenerateRequest;
import org.devgraft.support.jwt.JwtService;
import org.devgraft.support.provider.SHA256Provider;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class JwtAuthServiceImpl implements JwtAuthService {
    private final SHA256Provider sha256Provider;
    private final MemberService memberService;
    private final JwtService jwtService;

    @Override
    public JwtAuthResult refresh(final String accessToken, final String refresh) {
        Claims claims = jwtService.getBody(refresh);
        if (!Objects.equals(claims.getSubject(), sha256Provider.encrypt(accessToken, "CRYPT_LOGIN_TOKEN"))) throw new RuntimeException();
        return issuedMemberAuthToken(Long.valueOf(claims.getAudience()));
    }

    @Override
    public JwtAuthResult issue(final String identifyToken) {
        Long memberId = memberService.getMemberId(identifyToken);
        return issuedMemberAuthToken(memberId);
    }

    private JwtAuthResult issuedMemberAuthToken(Long memberId) {
        String access = jwtService.generateToken(JwtGenerateRequest.of("LOGIN", memberId.toString(), "ROLE_USER", 600));
        String cryptLoginToken = sha256Provider.encrypt(access, "CRYPT_LOGIN_TOKEN");
        String refresh = jwtService.generateToken(JwtGenerateRequest.of(cryptLoginToken, memberId.toString(), "ROLE_USER", 3600 * 30 * 12));
        return JwtAuthResult.of(access, refresh);
    }
}
