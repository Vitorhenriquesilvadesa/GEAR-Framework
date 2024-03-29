package org.gear.framework.core.engine.setting;

import org.gear.framework.core.application.Game;
import org.gear.framework.core.math.Vector2;

public class SettingsClassBuilder {

    private Vector2 windowSize;
    private boolean isFullScreen;
    private String windowTitle;


    private int maxTicksPerSecond;
    private boolean useColdAnnotations;
    private String rootDirectory;
    private Class<? extends Game> gameClass;

    private boolean showLogs;
    private boolean generateCriticalFiles;
    private boolean enableFileTracking;


    public SettingsClassBuilder() {

    }

    public SettingsClassBuilder windowSize(Vector2 windowSize) {
        this.windowSize = windowSize;
        return this;
    }

    public SettingsClassBuilder isFullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        return this;
    }

    public SettingsClassBuilder windowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }

    public SettingsClassBuilder maxTicksPerSecond(int maxTicksPerSecond) {
        this.maxTicksPerSecond = maxTicksPerSecond;
        return this;
    }

    public SettingsClassBuilder useColdAnnotations(boolean useColdAnnotations) {
        this.useColdAnnotations = useColdAnnotations;
        return this;
    }

    public SettingsClassBuilder rootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
        return this;
    }

    public SettingsClassBuilder gameClass(Class<? extends Game>  gameClass) {
        this.gameClass = gameClass;
        return this;
    }

    public SettingsClassBuilder showLogs(boolean showLogs) {
        this.showLogs = showLogs;
        return this;
    }

    public SettingsClassBuilder generateCriticalFiles(boolean generateCriticalFiles) {
        this.generateCriticalFiles = generateCriticalFiles;
        return this;
    }

    public SettingsClassBuilder enableFileTracking(boolean enableFileTracking) {
        this.enableFileTracking = enableFileTracking;
        return this;
    }

    public GearSettings buildGearSettings() {
        return new GearSettings(maxTicksPerSecond, useColdAnnotations, rootDirectory, gameClass);
    }

    public WindowSettings buildWindowSettings() {
        return new WindowSettings(windowSize, isFullScreen, windowTitle);
    }

    public DebugSettings buildDebugSettings() {
        return new DebugSettings(showLogs, generateCriticalFiles, enableFileTracking);
    }
}
