package org.gear.framework.core.service.audio;

import org.gear.framework.core.service.general.ApplicationService;
import org.gear.framework.core.log.LogLevel;
import org.gear.framework.core.log.Logger;
import org.gear.framework.core.log.annotation.LogInfo;

import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL;

@LogInfo(level = LogLevel.INFO)
public class AudioAPI extends Logger implements ApplicationService {

    boolean isPlayingInitAudio = false;

    @Override
    public boolean init() {
        return true;
    }

    @Override
    public void update() {
        if (!isPlayingInitAudio) {
            play();
        }
    }

    public void play() {
    }

    @Override
    public void shutdown() {

    }
}
