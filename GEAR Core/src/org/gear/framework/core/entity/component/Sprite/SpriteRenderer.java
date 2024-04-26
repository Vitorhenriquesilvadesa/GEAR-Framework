package org.gear.framework.core.entity.component.Sprite;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.service.rendering.element.model.Model;


public class SpriteRenderer extends Component {
    private Model model;

    public SpriteRenderer(GameObject parent) {
        super(parent);
    }

    @Override
    public void dispose() {

    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
