package org.gear.framework.core.service.general.factory;

import org.gear.framework.core.service.pool.ObjectPoolAPI;
import org.gear.framework.core.service.audio.AudioAPI;
import org.gear.framework.core.service.general.ApplicationService;
import org.gear.framework.core.service.event.EventAPI;
import org.gear.framework.core.service.input.InputAPI;
import org.gear.framework.core.service.general.provider.ServiceType;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.gear.framework.core.service.general.exception.UnknownServiceException;

public class ApplicationServiceFactory {

    public ApplicationServiceFactory() {
    }

    public ApplicationService createService(ServiceType type) {
        return switch (type) {
            case GRAPHICS_API -> new GraphicsAPI();
            case AUDIO_API -> new AudioAPI();
            case EVENT_API -> new EventAPI();
            case INPUT_API -> new InputAPI();
            case POOL_API -> new ObjectPoolAPI();
            default -> throw new UnknownServiceException(type);
        };
    }
}
