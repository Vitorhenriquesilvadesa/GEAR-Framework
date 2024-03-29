package org.gear.framework.core.service.rendering.window;

public class WindowBuilder {

    private int width;
    private int height;
    private String title;
    private boolean withCoreProfile;

    public WindowBuilder() {

    }

    public WindowBuilder width(int width) {
        this.width = width;
        return this;
    }

    public WindowBuilder height(int height) {
        this.height = height;
        return this;
    }

    public WindowBuilder title(String title) {
        this.title = title;
        return this;
    }

    public WindowBuilder withCoreProfile(boolean withCoreProfile) {
        this.withCoreProfile = withCoreProfile;
        return this;
    }

    public Window build() {
        return new Window(width, height, title, withCoreProfile);
    }
}
