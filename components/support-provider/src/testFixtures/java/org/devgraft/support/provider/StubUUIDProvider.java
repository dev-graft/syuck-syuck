package org.devgraft.support.provider;

import java.util.UUID;

public class StubUUIDProvider extends UUIDProvider {
    public UUID randomUUID_returnValue = UUID.randomUUID();
    @Override
    public UUID randomUUID() {
        return randomUUID_returnValue;
    }
}
