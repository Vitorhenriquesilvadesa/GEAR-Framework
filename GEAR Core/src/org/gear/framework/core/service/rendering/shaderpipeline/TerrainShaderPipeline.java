package org.gear.framework.core.service.rendering.shaderpipeline;

import org.gear.framework.core.log.annotation.LogAlias;
import org.gear.framework.core.math.Mathf;
import org.gear.framework.core.math.Matrix4f;
import org.gear.framework.core.service.rendering.element.light.DirectionalLight;
import org.gear.framework.core.service.rendering.renderer.config.Camera;

@LogAlias("Asset Loader")
public class TerrainShaderPipeline extends ShaderPipeline {

    private static final String VERTEX_FILE = "GEAR Core/src/resources/shaders/terrain.vert";
    private static final String FRAGMENT_FILE = "GEAR Core/src/resources/shaders/terrain.frag";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_smoothness;
    private int location_reflectivity;

    public TerrainShaderPipeline() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = getUniformLocation("transformationMatrix");
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_viewMatrix = getUniformLocation("viewMatrix");
        location_lightPosition = getUniformLocation("lightPosition");
        location_lightColor = getUniformLocation("lightColor");
        location_reflectivity = getUniformLocation("reflectivity");
        location_smoothness = getUniformLocation("smoothness");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureSampler");
        super.bindAttribute(2, "normal");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Mathf.createViewMatrix(camera);
        loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadDirectionalLight(DirectionalLight light) {
        loadVector(location_lightPosition, light.position);
        loadVector(location_lightColor, light.color);
    }

    public void loadMaterialVariables(float smooth, float reflectivity) {
        loadFloat(location_smoothness, smooth);
        loadFloat(location_reflectivity, reflectivity);
    }
}
