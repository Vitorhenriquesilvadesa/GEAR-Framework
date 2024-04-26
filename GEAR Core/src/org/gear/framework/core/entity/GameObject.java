package org.gear.framework.core.entity;


import org.gear.framework.core.service.rendering.scene.SceneUUID;

import java.util.Objects;

public class GameObject extends ComponentContainer {

    public Transform transform;
    private GameObjectUUID uuid;
    private SceneUUID sceneUUID;

    public GameObject() {
        this.transform = new Transform();
        this.uuid = new GameObjectUUID();
        setOwner(this);
    }

    public GameObject(Transform transform) {
        this.transform = transform;
        this.uuid = new GameObjectUUID();
        setOwner(this);
    }

    public SceneUUID getSceneUUID() {
        return sceneUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(sceneUUID, that.sceneUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, sceneUUID);
    }

    public GameObjectUUID getUUID() {
        return this.uuid;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
