package org.gear.framework.core.entity;

import org.gear.framework.core.entity.component.Component;

public class NullComponent extends Component {

    private static final NullComponent instance = new NullComponent(new NullGameObject());
    private NullComponent(GameObject parent) {
        super(parent);
    }

    @Override
    public void dispose() {

    }

    public static Component get() {
        return instance;
    }
}
