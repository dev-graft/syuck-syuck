package org.devgraft.authstore.provider;

import com.dreamsecurity.token.provider.UuidProvider;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class StubUuidProvider extends UuidProvider {
    public List<UUID> randomUUID = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
    public int index = 0;
    @Override
    public UUID randomUUID() {
        return this.randomUUID.get(index++);
    }
}
