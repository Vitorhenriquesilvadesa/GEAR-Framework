package org.gear.framework.core.entity;

import org.gear.framework.core.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Transform {
    private Vector3 position;
    private Vector3 rotation;
    private Vector3 scale;
    private final List<Transform> children = new ArrayList<>();
    private Transform parent;

    public Transform() {
        this.position = Vector3.zero();
        this.rotation = Vector3.zero();
        this.scale = new Vector3(1f, 1f, 1f);
    }

    public Transform(Vector3 position, Vector3 rotation, Vector3 scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Transform(Vector3 position, Vector3 rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3(scale, scale, scale);
    }

    public Transform(Transform parent) {
        this.position = Vector3.zero();
        this.rotation = Vector3.zero();
        this.scale = new Vector3(1f, 1f, 1f);
        this.parent = parent;
        this.parent.addChild(this);
    }

    public Transform(Vector3 position, Vector3 rotation, Vector3 scale, Transform parent) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.parent = parent;
        this.parent.addChild(this);
    }

    public Transform(Vector3 position, Vector3 rotation, float scale, Transform parent) {
        this.position = position;
        this.rotation = rotation;
        this.scale = new Vector3(scale, scale, scale);
        this.parent = parent;
        this.parent.addChild(this);
    }

    private void addChild(Transform transform) {
        this.children.add(transform);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getRotation() {
        return rotation;
    }

    public Vector3 getScale() {
        return scale;
    }

    public void setPosition(Vector3 position) {
        Vector3 deltaPosition = Vector3.subtract(position, this.position);
        for (Transform child : children) {
            Vector3 childPosition = Vector3.add(child.position, deltaPosition);
            child.setPosition(childPosition);
        }
        this.position = position;
    }

    public void setDepthLocation(int depthLocation) {
        Vector3 newPosition = new Vector3(position.x, position.y, depthLocation);
        Vector3 deltaPosition = Vector3.subtract(newPosition, this.position);
        for (Transform child : children) {
            Vector3 childPosition = Vector3.add(child.position, deltaPosition);
            child.setPosition(childPosition);
        }
        this.position = newPosition;
    }

    public void setRotation(Vector3 rotation) {
        Vector3 deltaRotation = Vector3.subtract(rotation, this.rotation);
        for (Transform child : children) {
            Vector3 childRotation = Vector3.add(child.rotation, deltaRotation);
            child.setRotation(childRotation);
        }
        this.rotation = rotation;
    }

    public void setScale(Vector3 scale) {
        Vector3 deltaScale = Vector3.divide(Vector3.subtract(scale, this.scale), this.scale);
        for (Transform child : children) {
            Vector3 childScale = Vector3.multiply(child.scale, deltaScale);
            child.setScale(childScale);
        }
        this.scale = scale;
    }

    public void setScale(float scale) {
        Vector3 newScale = new Vector3(scale, scale, scale);
        Vector3 deltaScale = Vector3.divide(Vector3.subtract(newScale, this.scale), this.scale);
        for (Transform child : children) {
            Vector3 childScale = Vector3.multiply(child.scale, deltaScale);
            child.setScale(childScale);
        }
        this.scale = newScale;
    }
}
