package org.gear.framework.core.engine;

import org.gear.framework.core.engine.setting.SettingsClassBuilder;
import org.gear.framework.core.engine.setting.SettingsFileReader;

public class EngineStarter {

    public EngineStarter(String configFilepath) {
        Engine engine = Engine.getInstance();
        SettingsClassBuilder settingsClassBuilder = SettingsFileReader.readFile(configFilepath);
        engine.setDebugSettings(settingsClassBuilder.buildDebugSettings());
        engine.setGearSettings(settingsClassBuilder.buildGearSettings());
        engine.setWindowSettings(settingsClassBuilder.buildWindowSettings());
    }
}
