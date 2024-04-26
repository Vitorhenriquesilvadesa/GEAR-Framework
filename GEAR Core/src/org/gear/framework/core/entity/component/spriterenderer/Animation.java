package org.gear.framework.core.entity.component.spriterenderer;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<Frame> frames = new ArrayList<>();

    public Animation(List<Sprite> sprites, int duration) {
        for (Sprite sprite : sprites) {
            frames.add(new Frame(sprite.texture(), duration));
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }
}
