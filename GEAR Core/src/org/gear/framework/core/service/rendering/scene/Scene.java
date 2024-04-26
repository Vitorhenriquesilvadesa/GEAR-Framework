package org.gear.framework.core.service.rendering.scene;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.log.annotation.GenerateCriticalFile;
import org.gear.framework.core.service.general.AssetLoader;

import java.util.ArrayList;
import java.util.List;

@GenerateCriticalFile
public class Scene extends AssetLoader {

    private final String name;
    private final SceneUUID id;
    private final List<Layer> layers;

    public Scene(String name) {

        if(name.replaceAll(" ", "").replaceAll("\t", "").replace("\n", "").isEmpty()) {
            critical("The layer name must contain a valid String without [\\t, \\n, ' ']");
        }
        this.layers = new ArrayList<>();
        this.id = new SceneUUID();
        this.name = name;
    }

    public void addLayer(String name) {
        this.layers.addFirst(new Layer(name));
    }

    public void addObject(GameObject gameObject, String layerName) {
        this.layers.stream()
                .filter((Layer layer) -> layer.getLayerName().equals(layerName))
                .findFirst().orElseThrow().addObject(gameObject);

        int layerIndex = 0;

        for (Layer layer : layers) {
            if (!layer.getLayerName().equals(layerName)) layerIndex++;
            else break;
        }

        gameObject.transform.setDepthLocation(layerIndex);
    }

    public void addObject(GameObject gameObject, int layerIndex) {
        this.layers.get(layerIndex).addObject(gameObject);
        gameObject.transform.setDepthLocation(layerIndex);
    }

    public List<GameObject> getGameObjects() {
        List<GameObject> gameObjects = new ArrayList<>();

        for (Layer layer : layers) {
            gameObjects.addAll(layer.getGameObjects());
        }

        return gameObjects;
    }

    public SceneUUID getUUID() {
        return id;
    }

    public void removeObject(GameObject gameObject) {
        for (Layer layer : this.layers) {
            layer.tryKill(gameObject);
        }
    }

    public void killAll() {
        for (Layer layer : this.layers) {
            layer.killAll();
        }
    }

    public String getName() {
        return name;
    }

    public SceneUUID getId() {
        return id;
    }
}
