package org.devgraft.auth.service;

import lombok.RequiredArgsConstructor;
import org.devgraft.jwt.provider.JwtToken;
import org.devgraft.jwt.provider.JwtTokenProvider;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenGenerateResponse generateToken(TokenGenerateRequest request) {
        JwtToken jwtToken = jwtTokenProvider.generate(request.getValidity(), request.getRefreshValidity());

        return new TokenGenerateResponse(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
