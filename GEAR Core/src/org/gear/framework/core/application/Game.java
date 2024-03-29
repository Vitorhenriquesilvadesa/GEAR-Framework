package org.gear.framework.core.application;

import org.gear.framework.core.log.*;
import org.gear.framework.core.log.annotation.*;

@LogInfo(level = LogLevel.INFO)
public abstract class Game extends Logger {

    public abstract void start();
    public abstract void update();
}
