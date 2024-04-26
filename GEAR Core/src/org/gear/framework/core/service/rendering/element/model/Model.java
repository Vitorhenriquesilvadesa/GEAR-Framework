package org.gear.framework.core.service.rendering.element.model;

import org.gear.framework.core.service.rendering.element.material.Material;
import org.gear.framework.core.service.rendering.element.material.Texture;

public class Model {

    private Mesh mesh;
    private Material material;

    public Model(Mesh model, Material material) {
        this.mesh = model;
        this.material = material;
    }

    public Model(Mesh model, Texture texture) {
        this.mesh = model;
        this.material = new Material(texture, 0, 0, true, true);
    }

    public Mesh getRawModel() {
        return mesh;
    }

    public Material getMaterial() {
        return material;
    }

    public void setRawModel(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
