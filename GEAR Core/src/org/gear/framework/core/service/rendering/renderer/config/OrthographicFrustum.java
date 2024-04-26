package org.gear.framework.core.service.rendering.renderer.config;

import org.gear.framework.core.math.Matrix4f;

public class OrthographicFrustum extends Frustum {

    private final float left;
    private final float right;
    private final float bottom;
    private final float top;
    private final float near;
    private final float far;

    public OrthographicFrustum(float left, float right, float bottom, float top, float near, float far) {
        super(Matrix4f.orthographic(left, right, bottom, top, near, far));
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
    }

    public float getLeft() {
        return left;
    }

    public float getRight() {
        return right;
    }

    public float getBottom() {
        return bottom;
    }

    public float getTop() {
        return top;
    }

    public float getNear() {
        return near;
    }

    public float getFar() {
        return far;
    }
}
