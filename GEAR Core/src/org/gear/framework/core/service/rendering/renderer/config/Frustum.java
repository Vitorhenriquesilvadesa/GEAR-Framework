package org.gear.framework.core.service.rendering.renderer.config;

import org.gear.framework.core.math.Matrix4f;

public class Frustum {
    private final Matrix4f projectionMatrix;

    protected Frustum(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
