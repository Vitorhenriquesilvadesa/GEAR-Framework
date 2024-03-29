package org.gear.framework.core.engine.setting;

import org.gear.framework.core.math.Vector2;

public class WindowSettings {

    private Vector2 windowSize;
    private boolean isFullScreen;
    private String windowTitle;

    public WindowSettings(Vector2 windowSize, boolean isFullScreen, String windowTitle) {
        this.windowSize = windowSize;
        this.isFullScreen = isFullScreen;
        this.windowTitle = windowTitle;
    }

    public Vector2 getWindowSize() {
        return windowSize;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public String getWindowTitle() {
        return windowTitle;
    }
}
