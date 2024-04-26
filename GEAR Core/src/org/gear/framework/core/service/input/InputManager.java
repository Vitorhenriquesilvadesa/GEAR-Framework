package org.gear.framework.core.service.input;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.math.Vector2;

public class InputManager {

    private InputAPI inputAPI;
    private boolean isInitialized = false;

    protected void init() {
        if(!isInitialized) {
            inputAPI = Engine.fromService(InputAPI.class);
            isInitialized = true;
        } else {
            throw new IllegalStateException("The Input Manager cannot initialized multiple times.");
        }
    }

    public boolean isKeyPressed(int keyCode) {
        return inputAPI.isKeyPressed(keyCode);
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyPressed(keyCode);
    }

    public boolean isMouseButtonPressed(int button) {
        return inputAPI.isMouseButtonPressed(button);
    }

    public boolean isMouseButtonReleased(int button) {
        return !isMouseButtonPressed(button);
    }

    public Vector2 getMousePosition() {
        return inputAPI.getMousePosition();
    }
}
