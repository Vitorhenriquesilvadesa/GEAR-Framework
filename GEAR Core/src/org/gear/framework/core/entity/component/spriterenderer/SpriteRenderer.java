package org.gear.framework.core.entity.component.spriterenderer;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.entity.component.box2dmesh.Box2DMesh;
import org.gear.framework.core.entity.component.box2dmesh.Box2DSize;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.gear.framework.core.service.rendering.element.material.Texture;


public class SpriteRenderer extends Component {

    private Texture texture;
    private boolean meshSizeDefined = false;

    public SpriteRenderer(GameObject parent) {
        super(parent);
        //Engine.fromService(GraphicsAPI.class).putObjectInScene(parent, parent.getLayerName());
    }

    public void start() {

    }

    public void update() {
        if(!meshSizeDefined) {
            if(texture != null) {
                getComponent(Box2DMesh.class).setProps(new Box2DSize(texture.getWidth(), texture.getHeight()));
                meshSizeDefined = true;
            }
        }
    }

    public void dispose() {
        Engine.fromService(GraphicsAPI.class).removeObjectFromScene(parent);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
