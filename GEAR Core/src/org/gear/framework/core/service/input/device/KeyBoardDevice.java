package org.gear.framework.core.service.input.device;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBoardDevice extends InputDevice {

    public KeyBoardDevice() {
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    @Override
    public void updateState() {

    }
}
