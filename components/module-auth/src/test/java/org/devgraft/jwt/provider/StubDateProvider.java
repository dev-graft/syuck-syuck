package org.devgraft.jwt.provider;

import java.util.Date;

public class StubDateProvider extends DateProvider {
    public Date date = new Date();
    @Override
    public Date generate() {
        return date;
    }
}
