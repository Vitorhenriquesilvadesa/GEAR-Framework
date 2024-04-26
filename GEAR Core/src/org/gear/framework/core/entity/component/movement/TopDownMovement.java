package org.gear.framework.core.entity.component.movement;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.math.Vector3;

public class TopDownMovement extends Component {

    public TopDownMovement(GameObject parent) {
        super(parent);
    }

    public void move(Vector2 delta) {
        delta = Vector2.normalize(delta);
        transform.setPosition(Vector3.add(new Vector3(delta.x, delta.y, 0f), transform.getPosition()));
    }
}