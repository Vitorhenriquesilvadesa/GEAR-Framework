package org.gear.framework.core.entity.component;


import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.Transform;
import org.gear.framework.core.service.event.EventAPI;

public abstract class Component {

    protected GameObject parent;
    protected Transform transform;
    public Component(GameObject parent) {
        this.parent = parent;
        this.transform = parent.transform;
        Engine.fromService(EventAPI.class).subscribe(this);
        Engine.fromService(EventAPI.class).resolveDependencies(this);
    }

    public void start() { }

    public void update() { }

    public void dispose() { }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    protected <T extends Component> T getComponent(Class<T> componentClass) {
        return parent.getComponent(componentClass);
    }

    protected <T extends Component> T addComponent(Class<T> componentClass) {
        return parent.addComponent(componentClass);
    }

    protected <T extends Component> T removeComponent(Class<T> componentClass) {
        return parent.removeComponent(componentClass);
    }
}
