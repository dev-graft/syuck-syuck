package org.devgraft.jwt.provider;

import org.devgraft.auth.provider.DateProvider;

import java.util.Date;

public class StubDateProvider extends DateProvider {
    public Date date = new Date();
    @Override
    public Date generate() {
        return date;
    }
}
