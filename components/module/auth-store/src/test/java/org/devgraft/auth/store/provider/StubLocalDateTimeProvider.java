package org.devgraft.auth.store.provider;

import org.devgraft.simple.provider.LocalDateTimeProvider;

import java.time.LocalDateTime;

public class StubLocalDateTimeProvider extends LocalDateTimeProvider {
    public LocalDateTime now = LocalDateTime.now();
    @Override
    public LocalDateTime now() {
        return this.now;
    }
}

