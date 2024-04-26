package org.gear.framework.core.service.general.factory;

import org.gear.framework.core.service.input.device.GamepadDevice;
import org.gear.framework.core.service.input.device.InputDevice;
import org.gear.framework.core.service.input.device.KeyBoardDevice;
import org.gear.framework.core.service.input.device.MouseDevice;
import org.gear.framework.core.service.general.provider.DeviceType;

public class InputDeviceFactory {

    public InputDevice createDevice(DeviceType type) {
        return switch(type) {
            case MOUSE -> new MouseDevice();
            case KEYBOARD -> new KeyBoardDevice();
            case GAMEPAD -> new GamepadDevice();
        };
    }
}
