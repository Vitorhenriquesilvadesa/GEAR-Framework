package org.gear.framework.core.entity.component.Sprite;

import org.gear.framework.core.service.rendering.element.material.Texture;

public class Frame {
    private Texture texture;
    private float duration;

    public Frame(Texture texture, float duration) {
        this.texture = texture;
        this.duration = duration;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getDuration() {
        return duration;
    }
}
