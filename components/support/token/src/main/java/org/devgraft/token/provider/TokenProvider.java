package org.devgraft.token.provider;

import org.devgraft.token.domain.Token;

public interface TokenProvider <T extends Token<?>, G extends TokenGenerateRequest> {
    T generate(G request);
}
