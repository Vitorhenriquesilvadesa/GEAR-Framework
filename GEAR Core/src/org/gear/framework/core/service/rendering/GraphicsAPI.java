package org.gear.framework.core.service.rendering;

import org.gear.framework.core.entity.component.Sprite.SpriteSheet;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.camera.CameraComponent;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.math.Vector3;
import org.gear.framework.core.service.event.actions.WindowResizeEvent;
import org.gear.framework.core.service.general.ApplicationService;
import org.gear.framework.core.log.Logger;
import org.gear.framework.core.service.event.EventListener;
import org.gear.framework.core.service.event.actions.Event;
import org.gear.framework.core.service.rendering.element.light.DirectionalLight;
import org.gear.framework.core.service.rendering.element.loader.ImageFilter;
import org.gear.framework.core.service.rendering.element.loader.ObjLoader;
import org.gear.framework.core.service.rendering.element.loader.TextureLoader;
import org.gear.framework.core.service.rendering.element.material.Material;
import org.gear.framework.core.service.rendering.element.material.Texture;
import org.gear.framework.core.service.rendering.element.model.Mesh;
import org.gear.framework.core.service.rendering.element.loader.MeshLoader;
import org.gear.framework.core.service.rendering.element.terrain.Terrain;
import org.gear.framework.core.service.rendering.renderer.config.Camera;
import org.gear.framework.core.service.rendering.renderer.MasterRenderer;
import org.gear.framework.core.service.rendering.scene.Scene;
import org.gear.framework.core.service.rendering.scene.SceneStack;
import org.gear.framework.core.service.rendering.shaderpipeline.ShaderPipelineBuilder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GraphicsAPI extends Logger implements ApplicationService, EventListener {

    private GraphicContext graphicsContext;
    private ShaderPipelineBuilder shaderPipelineBuilder;
    private MasterRenderer renderer;
    private MeshLoader meshLoader;
    private TextureLoader textureLoader;
    private ObjLoader objLoader;
    private Camera activeCamera;
    public DirectionalLight directionalLight;
    private List<Terrain> terrains;
    private Vector2 viewportSize;
    private Vector2 windowSize;
    private SceneStack sceneStack;
    private boolean isFullscreen = false;

    @Override
    public boolean init() {
        this.graphicsContext = new GraphicContext();

        if (this.graphicsContext.coreProfileIsEnabled()) {
            info("OpenGL running in Core Profile.");
        }
        this.terrains = new ArrayList<>();
        this.meshLoader = new MeshLoader();
        this.objLoader = new ObjLoader();
        this.textureLoader = new TextureLoader();
        this.shaderPipelineBuilder = new ShaderPipelineBuilder();
        this.renderer = new MasterRenderer();
        this.activeCamera = new Camera(new Vector3(0, 0, -10), Vector3.zero(), new Vector3(1, 1, 1));
        this.directionalLight = new DirectionalLight(new Vector3(0, 5, 0), new Vector3(1, 1, 1));
        this.graphicsContext.setCallbacks();
        this.windowSize = new Vector2(1280f, 720f);
        this.sceneStack = new SceneStack();
        return true;
    }

    @Override
    public void update() {
        graphicsContext.render();

        for (GameObject model : sceneStack.toGameObjectList()) {
            renderer.processEntity(model);
        }
        for (Terrain terrain : terrains) {
            renderer.processTerrain(terrain);
        }
        renderer.render(directionalLight, activeCamera);
        graphicsContext.update();
    }

    public void setViewportSize(float viewportWidth, float viewportHeight) {

        assert viewportWidth > 0f && viewportHeight > 0f : "Viewport size must be greater than zero.";
        this.renderer.setViewportSize(viewportWidth, viewportHeight);
        this.viewportSize = new Vector2(viewportWidth, viewportHeight);
    }

    public int loadShader(String file, int shaderType) {
        return this.shaderPipelineBuilder.loadShader(file, shaderType);
    }

    public Mesh loadModel(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        return this.meshLoader.loadToVAO(positions, textureCoords, normals, indices);
    }

    public Mesh loadModel(String file) {
        return this.objLoader.loadModel("Sandbox/src/resources/models/" + file, this.meshLoader);
    }

    public Texture loadTexture(String path, ImageFilter filter) {
        return this.textureLoader.getTexture("Sandbox/src/resources/textures/" + path, filter);
    }

    public Texture loadTexture(BufferedImage image, ImageFilter filter, int spriteWidth, int spriteHeight, int tileSize) {
        return this.textureLoader.getTexture(image, filter, spriteWidth, spriteHeight, tileSize);
    }

    public SpriteSheet loadSpriteSheet(String path, int sheetWidth, int sheetHeight, int row, int spriteWidth, int spriteHeight, int tileSize) {
        return new SpriteSheet("Sandbox/src/resources/textures/" + path, sheetWidth, sheetHeight, row, spriteWidth, spriteHeight, tileSize);
    }

    public Terrain loadTerrain(Vector2 position, String texturePath, ImageFilter filter) {
        return new Terrain((int) position.x, (int) position.y, this.meshLoader,
                new Material(textureLoader.getTexture("Sandbox/src/resources/textures/" + texturePath, filter),
                        0, 0, true));
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void setActiveCamera(GameObject cameraParent) {
        this.activeCamera = cameraParent.getComponent(CameraComponent.class).getCamera();
    }

    public void setActiveCamera(Camera camera) {
        this.activeCamera = camera;
    }

    public void putObjectInScene(GameObject gameObject, String layerName) {
        this.sceneStack.push(gameObject, layerName);
    }

    public void putObjectInScene(GameObject gameObject, int layerIndex) {
        this.sceneStack.push(gameObject, layerIndex);
    }

    public void pushSceneInCurrentStack(Scene scene) {
        this.sceneStack.pushScene(scene);
    }

    public void pushTerrainInRenderingPool(Terrain terrain) {
        this.terrains.add(terrain);
    }

    @Override
    public void shutdown() {
        this.meshLoader.cleanup();
        this.renderer.cleanup();
        graphicsContext.destroy();
    }

    public boolean checkWindowClosed() {
        return this.graphicsContext.isWindowClosed();
    }

    public long getNativeWindow() {
        return this.graphicsContext.getNativeWindow();
    }

    public void setWindowTitle(String newTitle) {
        this.graphicsContext.setWindowTitle(newTitle);
    }

    @Override
    public void onEvent(Event e) {
        if (e.getClass() == WindowResizeEvent.class) {
            this.windowSize.x = ((WindowResizeEvent) e).width;
            this.windowSize.y = ((WindowResizeEvent) e).height;
        }
    }

    public Vector2 getViewportSize() {
        return this.viewportSize;
    }

    public Vector2 getWindowSize() {
        return windowSize;
    }

    public void removeObjectFromScene(GameObject parent) {
        this.sceneStack.removeObject(parent);
    }

    public void setFullScreen(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }
}
