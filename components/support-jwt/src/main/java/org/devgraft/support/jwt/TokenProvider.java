package org.devgraft.support.jwt;

public interface TokenProvider <T extends Token<?>, G extends TokenGenerateRequest> {
    T generate(G request);
}
