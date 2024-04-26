package org.gear.framework.core.service.rendering;

import org.gear.framework.core.service.rendering.element.material.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureTable {

    private final Map<String, Texture> textureMap;

    public TextureTable() {
        this.textureMap = new HashMap<>();
    }

}
