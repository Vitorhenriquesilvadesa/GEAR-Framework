package org.gear.framework.core.service.rendering.scene;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.log.annotation.GenerateCriticalFile;
import org.gear.framework.core.service.general.AssetLoader;

import java.util.List;
import java.util.Stack;

@GenerateCriticalFile
public class SceneStack extends AssetLoader {
    private final Stack<Scene> scenes;

    public SceneStack() {
        this.scenes = new Stack<>();
    }

    public List<GameObject> toGameObjectList() {
        return scenes.peek().getGameObjects();
    }

    public void pushScene(Scene scene) {
        scenes.push(scene);
    }

    public void push(GameObject gameObject, String layerName) {
        if (scenes.isEmpty())
            critical("Empty scene stack. You must be create a first scene before instantiate Game Objects.");
        this.scenes.peek().addObject(gameObject, layerName);
    }

    public void push(GameObject gameObject, int layerIndex) {
        if (scenes.isEmpty())
            critical("Empty scene stack. You must be create a first scene before instantiate Game Objects.");
        this.scenes.peek().addObject(gameObject, layerIndex);
    }

    public void removeObject(GameObject parent) {
        scenes.peek().removeObject(parent);
    }

    public void killAll() {
        for (Scene scene : scenes) {
            scene.killAll();
        }
    }
}
