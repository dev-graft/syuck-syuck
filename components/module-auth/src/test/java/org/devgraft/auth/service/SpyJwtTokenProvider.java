package org.devgraft.auth.service;

import org.devgraft.jwt.provider.JwtToken;
import org.devgraft.jwt.provider.JwtTokenProvider;

public class SpyJwtTokenProvider extends JwtTokenProvider {
    public Long generate_validity_argument;
    public Long generate_refreshValidity_argument;
    public JwtToken generate_returnValue;

    public SpyJwtTokenProvider() {
        super(null, null);
    }

    @Override
    public JwtToken generate(Long validity, Long refreshValidity) {
        this.generate_validity_argument = validity;
        this.generate_refreshValidity_argument = refreshValidity;

        return generate_returnValue;
    }
}
