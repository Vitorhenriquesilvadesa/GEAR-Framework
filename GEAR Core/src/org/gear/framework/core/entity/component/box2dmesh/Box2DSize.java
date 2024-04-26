package org.gear.framework.core.entity.component.box2dmesh;

import org.gear.framework.core.math.Vector2;

public class Box2DSize {

    public int width;
    public int height;
    public Vector2 origin;

    public Box2DSize(int width, int height, Vector2 origin) {
        assert width >= 0.0f && height >= 0.0f : "Size of a box 2D must be greater than zero.";
        this.width = width;
        this.height = height;
        this.origin = origin;
    }

    public Box2DSize(int width, int height) {
        assert width >= 0.0f && height >= 0.0f : "Size of a box 2D must be greater than zero.";
        this.width = width;
        this.height = height;
        this.origin = null;
    }
}
