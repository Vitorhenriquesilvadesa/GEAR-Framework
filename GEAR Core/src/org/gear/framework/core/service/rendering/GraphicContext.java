package org.gear.framework.core.service.rendering;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.service.event.UpdatableComponent;
import org.gear.framework.core.service.event.actions.*;
import org.gear.framework.core.service.event.exception.UnknownKeyActionException;
import org.gear.framework.core.service.event.exception.UnknownMouseActionException;
import org.gear.framework.core.service.general.exception.ServiceInitializationException;
import org.gear.framework.core.service.rendering.element.RenderingElement;
import org.gear.framework.core.service.rendering.window.Window;
import org.gear.framework.core.service.rendering.window.WindowBuilder;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;

public class GraphicContext implements RenderingElement, UpdatableComponent {

    private final Window window;
    private final long nativeWindow;

    protected GraphicContext() {
        if(!glfwInit()) {
            throw new ServiceInitializationException(GraphicsAPI.class);
        }

        this.window = new WindowBuilder()
                .title(Engine.getTitle())
                .height(720)
                .width(1280)
                .withCoreProfile(true)
                .build();
        this.nativeWindow = window.getNativeWindow();
    }

    @Override
    public void render() {
        this.window.render();
    }

    public long getNativeWindow() {
        return this.nativeWindow;
    }

    public boolean coreProfileIsEnabled() {
        return this.window.isWithCoreProfile();
    }

    public boolean isWindowClosed() {
        return glfwWindowShouldClose(this.nativeWindow);
    }

    @Override
    public void update() {
        this.window.update();
    }

    public void destroy() {
        glfwDestroyWindow(this.nativeWindow);
        glfwTerminate();
    }

    public void setCallbacks() {
        glfwSetKeyCallback(getNativeWindow(), this::keyCallback);
        glfwSetMouseButtonCallback(getNativeWindow(), this::mouseButtonCallback);
        glfwSetCursorPosCallback(getNativeWindow(), this::mouseCursorPositionCallback);
        glfwSetScrollCallback(getNativeWindow(), this::mouseScrollCallback);
        glfwSetWindowSizeCallback(getNativeWindow(), this::windowSizeCallback);
        glfwSetWindowFocusCallback(getNativeWindow(), this::windowFocusCallback);
    }

    public void windowFocusCallback(long window, boolean focused) {
        Engine.fromService(GraphicsAPI.class).onEvent(new WindowFocusEvent(focused));
    }

    public void windowSizeCallback(long window, int width, int height) {
        this.window.setWidth(width);
        this.window.setHeight(height);
        glViewport(0, 0, width, height);
        Engine.fromService(GraphicsAPI.class).onEvent(new WindowResizeEvent(width, height));
    }

    public void mouseScrollCallback(long window, double xOffset, double yOffset) {
        Engine.fromService(GraphicsAPI.class).onEvent(new MouseCursorPositionEvent((float) xOffset, (float) yOffset));
    }

    public void mouseCursorPositionCallback(long window, double xPos, double yPos) {
        Engine.fromService(GraphicsAPI.class).onEvent(new MouseCursorPositionEvent((float) xPos, (float) yPos));
    }

    public void mouseButtonCallback(long window, int button, int action, int mods) {
        switch(action) {
            case GLFW_PRESS, GLFW_REPEAT: {
                Engine.fromService(GraphicsAPI.class).onEvent(new MouseButtonPressedEvent(button));
                return;
            }

            case GLFW_RELEASE: {
                Engine.fromService(GraphicsAPI.class).onEvent(new MouseButtonReleasedEvent(button));
                return;
            }

            default: {
                throw new UnknownMouseActionException(action);
            }
        }
    }

    public void keyCallback(long window, int key, int scanCode, int action, int mods) {

        switch(action) {
            case GLFW_PRESS, GLFW_REPEAT: {
                Engine.fromService(GraphicsAPI.class).onEvent(new KeyPressedEvent(key));
                return;
            }

            case GLFW_RELEASE: {
                Engine.fromService(GraphicsAPI.class).onEvent(new KeyReleasedEvent(key));
                return;
            }

            default: {
                throw new UnknownKeyActionException(action);
            }
        }
    }

    public void setWindowTitle(String newTitle) {
        this.window.setTitle(newTitle);
    }
}
