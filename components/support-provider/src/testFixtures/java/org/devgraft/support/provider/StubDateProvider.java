package org.devgraft.support.provider;

import java.util.Date;

public class StubDateProvider extends DateProvider {
    public Date now_returnValue = new Date();
    @Override
    public Date now() {
        return this.now_returnValue;
    }
}
