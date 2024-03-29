package org.gear.framework.core.service.rendering.window;


import org.gear.framework.core.service.event.UpdatableComponent;
import org.gear.framework.core.service.rendering.element.RenderingElement;
import org.gear.framework.core.math.Vector2;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL45.*;

public class Window implements RenderingElement, UpdatableComponent {

    private int width;
    private int height;
    private String title;
    private long nativeWindow;
    private final boolean withCoreProfile;

    public Window(int width, int height, String title, boolean withCoreProfile) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.withCoreProfile = withCoreProfile;
        initializeNative();
    }

    private void initializeNative() {
        this.nativeWindow = glfwCreateWindow(this.width, this.height, this.title, 0, 0);
        glfwMakeContextCurrent(this.nativeWindow);
        if (withCoreProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        }
        createCapabilities();
    }

    public long getNativeWindow() {
        return this.nativeWindow;
    }

    private void clear() {
        glClearColor(0.4f, 0.8f, 1.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    private void swapBuffers() {
        glfwSwapBuffers(this.nativeWindow);
    }

    private void pollEvents() {
        glfwPollEvents();
    }

    @Override
    public void render() {
        clear();
    }

    @Override
    public void update() {
        swapBuffers();
        pollEvents();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
        glfwSetWindowTitle(nativeWindow, title);
    }

    public int getHeight() {
        return height;
    }

    public Vector2 getResolution() {
        return new Vector2(this.width, this.height);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public boolean isWithCoreProfile() {
        return withCoreProfile;
    }
}
