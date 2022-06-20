package org.devgraft.support.token;

import org.devgraft.support.token.domain.Token;

public interface TokenProvider <T extends Token<?>, G extends TokenGenerateRequest> {
    T generate(G request);
}
