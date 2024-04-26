package org.gear.framework.core.service.rendering.element.light;

import org.gear.framework.core.math.Vector3;

public class Light {

    public Vector3 position;
    public Vector3 color;

    public Light(Vector3 position, Vector3 color) {
        this.position = position;
        this.color = color;
    }


}
