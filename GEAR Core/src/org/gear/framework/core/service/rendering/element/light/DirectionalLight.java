package org.gear.framework.core.service.rendering.element.light;

import org.gear.framework.core.math.Vector3;

public class DirectionalLight extends Light {
    public DirectionalLight(Vector3 position, Vector3 color) {
        super(position, color);
    }
}
