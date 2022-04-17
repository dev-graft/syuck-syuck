package org.devgraft.auth.service;

import lombok.RequiredArgsConstructor;
import org.devgraft.auth.jwt.JwtToken;
import org.devgraft.auth.jwt.JwtTokenProvider;
import org.devgraft.auth.provider.LocalDateTimeProvider;
import org.devgraft.auth.provider.UuidProvider;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final LocalDateTimeProvider localDateTimeProvider;
    private final UuidProvider uuidProvider;

    @Override
    public TokenGenerateResponse generateToken(TokenGenerateRequest request) {
        JwtToken jwtToken = jwtTokenProvider.generate(request.getValidity(), request.getRefreshValidity());
        String dataSignKey = uuidProvider.randomUUID().toString();

        AuthInformation authInformation = new AuthInformation(
                request.getRoles(), dataSignKey,
                request.getValidity(), request.getRefreshValidity(),
                jwtToken.getAccessToken(), jwtToken.getRefreshToken(),
                localDateTimeProvider.now());

        ValueOperations<String, Object> opsValue = redisTemplate.opsForValue();
        opsValue.set(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        opsValue.set(jwtToken.getRefreshToken(), authInformation);

        HashOperations<String, String, Object> opsHash = redisTemplate.opsForHash();
        opsHash.putAll(dataSignKey, request.getData());

        redisTemplate.expire(jwtToken.getAccessToken(), request.getValidity(), TimeUnit.MILLISECONDS);
        redisTemplate.expire(jwtToken.getRefreshToken(), request.getRefreshValidity(), TimeUnit.MILLISECONDS);
        redisTemplate.expire(dataSignKey, request.getRefreshValidity(), TimeUnit.MILLISECONDS);

        return new TokenGenerateResponse(jwtToken.getAccessToken(), jwtToken.getRefreshToken());
    }
}
