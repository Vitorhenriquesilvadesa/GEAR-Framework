package org.gear.framework.core.service.input;

import org.gear.framework.core.design_patterns.Singleton;
import org.gear.framework.core.design_patterns.injection.DependencyManager;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.service.general.ApplicationService;
import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.service.event.EventListener;
import org.gear.framework.core.service.event.actions.Event;
import org.gear.framework.core.service.input.exception.DisconnectedDeviceException;
import org.gear.framework.core.service.input.device.InputDevice;
import org.gear.framework.core.service.general.provider.DeviceType;
import org.gear.framework.core.service.general.provider.InputDeviceProvider;
import org.gear.framework.core.service.rendering.GraphicsAPI;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class InputAPI implements ApplicationService, EventListener {

    private final Map<DeviceType, InputDevice> inputDeviceMap;

    public InputAPI() {
        this.inputDeviceMap = new HashMap<>();
    }

    @Override
    public void onEvent(Event e) {

    }

    @Override
    public boolean init() {
        registerDevice(DeviceType.MOUSE);
        registerDevice(DeviceType.KEYBOARD);
        registerDevice(DeviceType.GAMEPAD);
        Singleton<InputManager> inputManagerSingleton = new Singleton<>(new InputManager());
        inputManagerSingleton.getInstance().init();

        DependencyManager.injectDependency(inputManagerSingleton.getInstance());
        return true;
    }

    @Override
    public void update() {
        for(InputDevice device : inputDeviceMap.values()){
            device.updateState();
        }
    }

    @Override
    public void shutdown() {

    }

    public void registerDevice(DeviceType deviceType) {
        InputDevice device = InputDeviceProvider.getDevice(deviceType);
        inputDeviceMap.put(deviceType, device);
    }

    public boolean isDeviceConnected(DeviceType deviceType) {
        return inputDeviceMap.get(deviceType).isConnected();
    }

    public boolean isKeyPressed(int keyCode) {
        if(isDeviceConnected(DeviceType.KEYBOARD)) {
            return glfwGetKey(Engine.fromService(GraphicsAPI.class).getNativeWindow(), keyCode) == 1;
        }

        throw new DisconnectedDeviceException(DeviceType.KEYBOARD);
    }

    public boolean isMouseButtonPressed(int button) {
        if(isDeviceConnected(DeviceType.MOUSE)) {
            return glfwGetMouseButton(Engine.fromService(GraphicsAPI.class).getNativeWindow(), button) == 1;
        }

        throw new DisconnectedDeviceException(DeviceType.MOUSE);
    }

    public Vector2 getMousePosition() {

        double[] x = new double[1];
        double[] y = new double[1];
        Vector2 viewportSize = Engine.fromService(GraphicsAPI.class).getViewportSize();
        Vector2 windowSize = Engine.fromService(GraphicsAPI.class).getWindowSize();

        float scalarX = windowSize.x / viewportSize.x;
        float scalarY = windowSize.y / viewportSize.y;

        if(isDeviceConnected(DeviceType.MOUSE)) {
            glfwGetCursorPos(Engine.fromService(GraphicsAPI.class).getNativeWindow(), x, y);
            return new Vector2((float) x[0] / scalarX, (float) y[0] / scalarY);
        }

        throw new DisconnectedDeviceException(DeviceType.MOUSE);
    }
}
