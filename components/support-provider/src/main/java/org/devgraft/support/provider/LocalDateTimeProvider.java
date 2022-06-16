package org.devgraft.support.provider;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeProvider {
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
