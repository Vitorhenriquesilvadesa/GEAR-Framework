package org.gear.framework.core.entity.component.collider;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;

public abstract class Collider extends Component {
    protected String tag;

    public Collider(GameObject parent) {
        super(parent);
    }
    public abstract boolean isCollision(Collider collider);

    public String getTag() {
        return tag;
    }
}
