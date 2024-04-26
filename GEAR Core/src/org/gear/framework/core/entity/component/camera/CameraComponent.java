package org.gear.framework.core.entity.component.camera;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.Transform;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.math.Vector3;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.gear.framework.core.service.rendering.renderer.config.Camera;

public class CameraComponent extends Component {

    private final Camera camera;
    public CameraComponent(GameObject parent) {
        super(parent);
        camera = new Camera();
    }

    public void start() {
        camera.transform = new Transform(parent.transform);
        Vector3 position = transform.getPosition();
        camera.transform.setPosition(new Vector3(position.x, position.y, position.z - 1));
    }



    public Camera getCamera() {
        return camera;
    }

    public void setToActive() {
        Engine.fromService(GraphicsAPI.class).setActiveCamera(camera);
    }
}
