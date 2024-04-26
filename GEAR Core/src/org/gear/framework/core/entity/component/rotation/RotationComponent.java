package org.gear.framework.core.entity.component.rotation;

import org.gear.framework.core.engine.Time;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.math.Vector3;

public class RotationComponent extends Component {

    public RotationComponent(GameObject parent) {
        super(parent);
    }

    @Override
    public void update() {
        Vector3 rotation = parent.transform.getRotation();
        float increment = 5f * Time.time();
        parent.transform.setRotation(new Vector3(rotation.x, rotation.y, increment));
    }
}
