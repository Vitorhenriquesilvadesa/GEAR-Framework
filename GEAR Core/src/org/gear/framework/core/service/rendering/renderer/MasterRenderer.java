package org.gear.framework.core.service.rendering.renderer;

import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.spriterenderer.SpriteRenderer;
import org.gear.framework.core.entity.component.box2dmesh.Box2DMesh;
import org.gear.framework.core.log.Logger;
import org.gear.framework.core.log.annotation.LogAlias;
import org.gear.framework.core.service.rendering.element.light.DirectionalLight;
import org.gear.framework.core.service.rendering.element.model.Model;
import org.gear.framework.core.service.rendering.element.terrain.Terrain;
import org.gear.framework.core.service.rendering.renderer.config.Camera;
import org.gear.framework.core.service.rendering.renderer.config.Frustum;
import org.gear.framework.core.service.rendering.renderer.config.OrthographicFrustum;
import org.gear.framework.core.service.rendering.shaderpipeline.EntityShaderPipeline;
import org.gear.framework.core.service.rendering.shaderpipeline.TerrainShaderPipeline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@LogAlias("Renderer")
public class MasterRenderer extends Logger {

    private final EntityShaderPipeline entityShaderPipeline = new EntityShaderPipeline();
    private final TerrainShaderPipeline terrainShaderPipeline = new TerrainShaderPipeline();
    private final EntityRenderer entityRenderer;
    private final TerrainRenderer terrainRenderer;
    private final Map<Model, List<GameObject>> entities = new HashMap<>();
    private long entityCount;
    private final List<Terrain> terrains = new ArrayList<>();
    private Frustum frustum;
    private final float viewportWidth = 16f;
    private final float viewportHeight = 9f;

    public MasterRenderer() {
        //this.frustum = new PerspectiveFrustum(70f, 0.1f, 1000f);
        this.frustum = new OrthographicFrustum(-viewportWidth / 2f, viewportWidth / 2f, -viewportHeight / 2f, viewportHeight / 2f, 0.1f, 100f);
        this.entityRenderer = new EntityRenderer(entityShaderPipeline, frustum);
        this.terrainRenderer = new TerrainRenderer(terrainShaderPipeline, frustum);
    }


    public void render(DirectionalLight directionalLight, Camera camera) {
        entityShaderPipeline.bind();
        entityShaderPipeline.loadProjectionMatrix(entityRenderer.getFrustum().getProjectionMatrix());
        entityShaderPipeline.loadDirectionalLight(directionalLight);
        entityShaderPipeline.loadViewMatrix(camera);
        entityRenderer.render(entities, entityCount);
        entityShaderPipeline.unbind();

        terrainShaderPipeline.bind();
        terrainShaderPipeline.loadProjectionMatrix(entityRenderer.getFrustum().getProjectionMatrix());
        terrainShaderPipeline.loadDirectionalLight(directionalLight);
        terrainShaderPipeline.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShaderPipeline.unbind();

        terrains.clear();
        entities.clear();
        entityCount = 0;
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processEntity(GameObject gameObject) {

        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        Box2DMesh mesh = gameObject.getComponent(Box2DMesh.class);

        if(spriteRenderer == null || mesh == null) return;

        Model model = new Model(mesh.getMesh(), spriteRenderer.getTexture());

        List<GameObject> batch = entities.get(model);

        if (batch != null) {
            batch.add(gameObject);
        } else {
            List<GameObject> newBatch = new ArrayList<>();
            newBatch.add(gameObject);
            entities.put(model, newBatch);
        }

        entityCount++;
    }

    public void cleanup() {
        entityShaderPipeline.cleanup();
        terrainShaderPipeline.cleanup();
    }

    public void setViewportSize(float viewportWidth, float viewportHeight) {
        float halfWidth = viewportWidth / 2f;
        float halfHeight = viewportHeight / 2f;

        this.frustum = new OrthographicFrustum(-halfWidth, halfWidth, -halfHeight, halfHeight, 0.1f, 1000f);
        this.entityRenderer.setFrustum(frustum);
        this.terrainRenderer.setFrustum(frustum);
    }
}
