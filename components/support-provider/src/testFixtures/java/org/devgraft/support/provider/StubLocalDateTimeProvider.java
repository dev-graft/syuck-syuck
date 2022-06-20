package org.devgraft.support.provider;


import java.time.LocalDateTime;

public class StubLocalDateTimeProvider extends LocalDateTimeProvider {
    public LocalDateTime now_returnValue = LocalDateTime.now();
    @Override
    public LocalDateTime now() {
        return this.now_returnValue;
    }
}