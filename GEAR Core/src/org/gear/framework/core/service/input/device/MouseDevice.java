package org.gear.framework.core.service.input.device;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.GLFW.*;

public class MouseDevice extends InputDevice {

    float x;
    float y;
    float sensitivity;

    public MouseDevice() {
        this.x = 0f;
        this.y = 0f;
        this.sensitivity = 0.5f;
    }

    @Override
    public boolean isConnected() {
        return this.isConnected;
    }

    @Override
    public void updateState() {
        double[] mouseX = new double[1];
        double[] mouseY = new double[1];

        glfwGetCursorPos(Engine.fromService(GraphicsAPI.class).getNativeWindow(), mouseX, mouseY);
        this.x = (float) mouseX[0];
        this.y = (float) mouseY[0];
    }
}
