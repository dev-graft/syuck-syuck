package org.devgraft.support.provider;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateProvider {
    public Date now() {
        return new Date();
    }
}
