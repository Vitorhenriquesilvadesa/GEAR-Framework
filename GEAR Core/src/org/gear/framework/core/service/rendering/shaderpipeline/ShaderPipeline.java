package org.gear.framework.core.service.rendering.shaderpipeline;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.math.Matrix4f;
import org.gear.framework.core.math.Vector3;
import org.gear.framework.core.service.general.AssetLoader;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL45.*;

public abstract class ShaderPipeline extends AssetLoader {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public ShaderPipeline(String vertexFile, String fragmentFile) {
        this.vertexShaderID = Engine.fromService(GraphicsAPI.class).loadShader(vertexFile, GL_VERTEX_SHADER);
        this.fragmentShaderID = Engine.fromService(GraphicsAPI.class).loadShader(fragmentFile, GL_FRAGMENT_SHADER);
        this.programID = glCreateProgram();

        glAttachShader(this.programID, this.vertexShaderID);
        glAttachShader(this.programID, this.fragmentShaderID);
        bindAttributes();
        glLinkProgram(this.programID);
        glValidateProgram(this.programID);
        getAllUniformLocations();
    }

    protected int getUniformLocation(String uniformName) {
        return glGetUniformLocation(this.programID, uniformName);
    }

    protected abstract void getAllUniformLocations();

    protected abstract void bindAttributes();

    protected void loadFloat(int location, float value) {
        glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3 vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if(value) {
            toLoad = 1;
        }

        glUniform1f(location, toLoad);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        glUniformMatrix4fv(location, false, matrix.getMatrix());
    }

    public void bind() {
        glUseProgram(this.programID);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        info("Unloading shader assets.");
        unbind();
        glDetachShader(this.programID, this.vertexShaderID);
        glDetachShader(this.programID, this.fragmentShaderID);
        glDeleteShader(this.vertexShaderID);
        glDeleteShader(this.fragmentShaderID);
        glDeleteProgram(this.programID);
    }

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(this.programID, attribute, variableName);
    }
}
