package org.devgraft.token.jwt.provider;

import org.devgraft.token.provider.DateProvider;

import java.util.Date;

public class StubDateProvider extends DateProvider {
    public Date date = new Date();
    @Override
    public Date now() {
        return date;
    }
}
