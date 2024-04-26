package org.gear.framework.core.service.rendering.element.loader;

import org.gear.framework.core.log.annotation.LogAlias;
import org.gear.framework.core.service.general.AssetLoader;
import org.gear.framework.core.service.rendering.element.model.Mesh;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL45.*;

@LogAlias("Asset Loader")
public class MeshLoader extends AssetLoader {
    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();
    private static boolean isCreated = false;

    public MeshLoader() {
        if(!isCreated) {
            isCreated = true;
        } else {
            throw new IllegalStateException("Must one RawModelLoader can created!");
        }
    }

    public Mesh loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new Mesh(vaoID, indices.length);
    }

    public int createVAO() {
        int vaoID = glCreateVertexArrays();
        vaos.add(vaoID);
        glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int attributeSize, float[] data) {
        int vboID = glCreateBuffers();
        vbos.add(vboID);
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
        glVertexAttribPointer(attributeNumber, attributeSize, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

    }

    public void cleanup() {

        info("Unloading textures.");

        for(int vao : vaos) {
            glDeleteVertexArrays(vao);
        }

        for(int vbo : vbos) {
            glDeleteBuffers(vbo);
        }

        for(int texture : textures) {
            glDeleteTextures(texture);
        }
    }

    private void unbindVAO() {
        glBindVertexArray(0);
    }

    public void bindIndicesBuffer(int[] indices) {
        int iboID = glCreateBuffers();
        vbos.add(iboID);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
    }

    public IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
