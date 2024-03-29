package org.gear.framework.core.application;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.engine.EngineStarter;
import org.gear.framework.core.log.LogLevel;
import org.gear.framework.core.log.Logger;
import org.gear.framework.core.log.annotation.LogInfo;
import org.gear.framework.core.log.annotation.NotDebugLog;
import org.gear.framework.core.service.event.EventAPI;
import org.gear.framework.core.service.event.EventFlag;

@LogInfo(level = LogLevel.INFO)
@NotDebugLog
public class Application extends Logger {
    public static void init(Class<? extends Game> gameClass, String configFilepath) {
        new EngineStarter(configFilepath);
        Engine.getInstance().init(gameClass);
    }

    public static void run() {
        EventAPI eventAPI = Engine.fromService(EventAPI.class);

        while(!eventAPI.getFlag(EventFlag.WINDOW_CLOSE)) {
            Engine.getInstance().update();
        }

        Application.shutdown();
    }

    public static void shutdown() {
        Engine.getInstance().shutdown();
    }
}
