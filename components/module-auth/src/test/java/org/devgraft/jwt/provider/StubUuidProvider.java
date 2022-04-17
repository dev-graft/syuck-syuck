package org.devgraft.jwt.provider;

import org.devgraft.auth.provider.UuidProvider;

import java.util.List;
import java.util.UUID;

public class StubUuidProvider extends UuidProvider {
    public List<UUID> randomUUID = List.of(UUID.randomUUID(), UUID.randomUUID());
    public int index = 0;
    @Override
    public UUID randomUUID() {
        return this.randomUUID.get(index++);
    }
}
