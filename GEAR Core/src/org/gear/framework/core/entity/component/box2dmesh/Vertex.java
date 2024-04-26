package org.gear.framework.core.entity.component.box2dmesh;

import org.gear.framework.core.math.Vector2;

public class Vertex extends Vector2 {

    public Vertex(float x, float y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
