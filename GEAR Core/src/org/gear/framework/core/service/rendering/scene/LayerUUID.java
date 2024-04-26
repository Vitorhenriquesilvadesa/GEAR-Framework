package org.gear.framework.core.service.rendering.scene;

import java.util.UUID;

public class LayerUUID {
    private final UUID id;

    public LayerUUID() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}


