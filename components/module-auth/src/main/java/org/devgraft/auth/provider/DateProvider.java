package org.devgraft.auth.provider;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateProvider {
    public Date generate() {
        return new Date();
    }
}
