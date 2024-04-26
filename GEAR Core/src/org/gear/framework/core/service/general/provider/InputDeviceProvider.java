package org.gear.framework.core.service.general.provider;

import org.gear.framework.core.design_patterns.Singleton;
import org.gear.framework.core.service.general.factory.InputDeviceFactory;
import org.gear.framework.core.service.input.device.InputDevice;

public class InputDeviceProvider {
    private final InputDeviceFactory deviceFactory = new InputDeviceFactory();
    private static final Singleton<InputDeviceProvider> providerSingleton = new Singleton<>(InputDeviceProvider.class);
    public static InputDevice getDevice(DeviceType type) {
        return providerSingleton.getInstance().deviceFactory.createDevice(type);
    }
}
