package org.gear.framework.core.service.rendering.element.material;

public class Material {

    private Texture texture;
    public float smoothness;
    public float reflectivity;
    public boolean isDoubleSided;
    public boolean fakeLighting = false;

    public Material(Texture texture, float smoothness, float reflectivity, boolean isDoubleSided) {
        this.texture = texture;
        this.smoothness = smoothness;
        this.reflectivity = reflectivity;
        this.isDoubleSided = isDoubleSided;
    }

    public Material(Texture texture, float smoothness, float reflectivity, boolean isDoubleSided, boolean useFakeLighting) {
        this.texture = texture;
        this.smoothness = smoothness;
        this.reflectivity = reflectivity;
        this.isDoubleSided = isDoubleSided;
        this.fakeLighting = useFakeLighting;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
