package org.gear.framework.core.service.input.exception;

import org.gear.framework.core.service.general.provider.DeviceType;

public class DisconnectedDeviceException extends RuntimeException {
    public DisconnectedDeviceException(DeviceType deviceType) {
        super("Provided device is not connected: " + deviceType.name());
    }
}
