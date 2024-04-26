package org.gear.framework.core.service.rendering.renderer.config;

import java.util.Objects;
import java.util.UUID;

public class RenderingUUID {

    private final long id;

    public RenderingUUID() {
        this.id = UUID.randomUUID().timestamp();
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RenderingUUID that = (RenderingUUID) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
