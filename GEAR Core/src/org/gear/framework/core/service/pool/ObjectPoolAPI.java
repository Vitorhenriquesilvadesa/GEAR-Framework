package org.gear.framework.core.service.pool;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.entity.Transform;
import org.gear.framework.core.log.Logger;
import org.gear.framework.core.service.general.ApplicationService;

import java.util.ArrayList;
import java.util.List;

public class ObjectPoolAPI extends Logger implements ApplicationService {
    private static final List<GameObject> gameObjects = new ArrayList<>();

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void update() {
        for(GameObject gameObject : gameObjects) {
            gameObject.updateComponents();
        }
    }

    @Override
    public void shutdown() {
        info("Cleaning up " + gameObjects.size() + " objects.");
        for (GameObject gameObject : gameObjects) {
            gameObject.clearComponents();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends Component> GameObject instantiate(Transform parent, Class<?>... components) {
        GameObject gameObject = new GameObject(parent);

        if (components != null) {
            for (Class<?> component : components) {
                gameObject.addComponent((Class<T>)component);
            }
        }

        for(Component component : gameObject.getComponents()) {
            component.start();
        }

        attachObject(gameObject);

        return gameObject;
    }

    public static GameObject instantiate(Transform parent) {
        GameObject gameObject = new GameObject(parent);

        for(Component component : gameObject.getComponents()) {
            component.start();
        }

        attachObject(gameObject);

        return gameObject;
    }

    public static GameObject instantiate() {
        GameObject gameObject = new GameObject();
        attachObject(gameObject);
        return gameObject;
    }

    public static void destroy(GameObject gameObject) {
        gameObject.clearComponents();
        gameObjects.remove(gameObject);
    }

    private static void attachObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }
}
