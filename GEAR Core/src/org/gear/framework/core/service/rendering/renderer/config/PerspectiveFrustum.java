package org.gear.framework.core.service.rendering.renderer.config;

import org.gear.framework.core.math.Matrix4f;

public class PerspectiveFrustum extends Frustum {

    public PerspectiveFrustum(float angleOfView, float near, float far) {
        super(Matrix4f.perspective(angleOfView, near, far));
    }
}
