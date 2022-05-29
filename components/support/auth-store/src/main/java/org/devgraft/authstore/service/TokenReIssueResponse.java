package org.devgraft.authstore.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TokenReIssueResponse {
    private final String accessToken;
    private final String refreshToken;
}
