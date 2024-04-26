package org.gear.framework.core.service.rendering.scene;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.service.rendering.GraphicsAPI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.gear.framework.core.service.pool.ObjectPoolAPI.*;

public class Layer {

    private final Vector2 parallax;
    private float scale;
    private final Vector2 resolution;
    private final LayerUUID uuid;
    private final String layerName;
    private final List<GameObject> gameObjects;

    public Layer(String name, Vector2 parallax, float scale, Vector2 resolution) {
        this.parallax = parallax;
        this.scale = scale;
        this.resolution = resolution;
        this.layerName = name;
        this.gameObjects = new ArrayList<>();
        this.uuid = new LayerUUID();
    }

    public Layer(String name) {
        this.parallax = new Vector2(100f, 100f);
        this.scale = 1f;
        this.resolution = Engine.fromService(GraphicsAPI.class).getViewportSize();
        this.layerName = name;
        this.gameObjects = new ArrayList<>();
        this.uuid = new LayerUUID();
    }

    public void tryKill(GameObject gameObject) {
        this.gameObjects.remove(gameObject);
    }

    public void killAll() {
        for (GameObject gameObject : gameObjects) {
            destroy(gameObject);
        }

        this.gameObjects.clear();
    }

    public void sortObjectsBy(Comparator<GameObject> comparator) {
        gameObjects.sort(comparator);
    }

    public Vector2 getParallax() {
        return parallax;
    }

    public float getScale() {
        return scale;
    }

    public Vector2 getResolution() {
        return resolution;
    }

    public LayerUUID getUuid() {
        return uuid;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public String getLayerName() {
        return layerName;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void addObject(GameObject gameObject) {
        this.gameObjects.add(gameObject);
        gameObjects.sort(Comparator.comparingDouble(obj -> obj.transform.getPosition().z));
    }
}
