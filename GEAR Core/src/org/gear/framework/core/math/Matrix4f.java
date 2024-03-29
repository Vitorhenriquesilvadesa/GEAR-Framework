package org.gear.framework.core.math;

/*
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class Matrix4f {
    private final float[][] matrix;

    public Matrix4f() {
        this.matrix = new float[4][4];
        setIdentity();
    }

    public Matrix4f(float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4 || matrix[1].length != 4 || matrix[2].length != 4 || matrix[3].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }

        this.matrix = matrix;
    }

    public void setIdentity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = (i == j) ? 1.0f : 0.0f;
            }
        }
    }

    public void translate(Vector3 translation) {
        matrix[0][3] += translation.x;
        matrix[1][3] += translation.y;
        matrix[2][3] += translation.z;
    }

    public void rotate(float angle, Vector3 axis) {
        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        float oneMinusC = 1.0f - c;

        float x = axis.x;
        float y = axis.y;
        float z = axis.z;

        float[][] rotationMatrix = {
                {c + x * x * oneMinusC, x * y * oneMinusC - z * s, x * z * oneMinusC + y * s, 0.0f},
                {y * x * oneMinusC + z * s, c + y * y * oneMinusC, y * z * oneMinusC - x * s, 0.0f},
                {z * x * oneMinusC - y * s, z * y * oneMinusC + x * s, c + z * z * oneMinusC, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        };

        multiply(new Matrix4f(rotationMatrix));
    }

    public void scale(Vector3 scale) {
        matrix[0][0] *= scale.x;
        matrix[1][1] *= scale.y;
        matrix[2][2] *= scale.z;
    }

    public void multiply(Matrix4f other) {
        float[][] result = new float[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0.0f;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.matrix[i][k] * other.matrix[k][j];
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            System.arraycopy(result[i], 0, this.matrix[i], 0, 4);
        }
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public static Matrix4f perspective(float fov, float aspectRatio, float near, float far) {
        Matrix4f result = new Matrix4f();

        float tanHalfFov = (float) Math.tan(Math.toRadians(fov / 2.0));
        float range = near - far;

        result.matrix[0][0] = 1.0f / (aspectRatio * tanHalfFov);
        result.matrix[1][1] = 1.0f / tanHalfFov;
        result.matrix[2][2] = (-near - far) / range;
        result.matrix[2][3] = 2 * far * near / range;
        result.matrix[3][2] = 1;
        result.matrix[3][3] = 0;

        return result;
    }

    public FloatBuffer toFloatBuffer() {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                floatBuffer.put(matrix[i][j]);
            }
        }

        floatBuffer.flip();
        return floatBuffer;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");

        for (int i = 0; i < 4; i++) {
            builder.append("\n");
            for (int j = 0; j < 4; j++) {
                builder.append(matrix[i][j]).append(", ");
            }
        }
        builder.append("\n");
        builder.append("}");
        return builder.toString();
    }
}
*/

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class Matrix4f {
    public float[] elements;

    public Matrix4f() {
        elements = new float[16];
        Arrays.fill(elements, 0.0f);
    }

    public static Matrix4f perspective(float fov, float aspectRatio, float nearPlane, float farPlane) {
        float y_scale = (1f / Mathf.tan(Mathf.toRadians(fov / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = farPlane - nearPlane;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.elements[0] = x_scale;
        projectionMatrix.elements[5] = y_scale;
        projectionMatrix.elements[10] = -((farPlane + nearPlane) / frustum_length);
        projectionMatrix.elements[11] = -1;
        projectionMatrix.elements[14] = -((2 * farPlane + nearPlane) / frustum_length);
        projectionMatrix.elements[15] = 0;

        return projectionMatrix;
    }

    public static Matrix4f perspective(float angleOfView, float near, float far) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        float scale = 1 / Mathf.tan(angleOfView * 0.5f * (float) Math.PI / 180);
        matrix.elements[0] = scale;
        matrix.elements[5] = scale;
        matrix.elements[10] = -far / (far - near);
        matrix.elements[14] = -far * near / (far - near);
        matrix.elements[11] = -1;
        matrix.elements[15] = 0;

        return matrix;
    }

    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[0] = 2.0f / (right - left);
        matrix.elements[5] = 2.0f / (top - bottom);
        matrix.elements[10] = -2.0f / (far - near);
        matrix.elements[12] = -(right + left) / (right - left);
        matrix.elements[13] = -(top + bottom) / (top - bottom);
        matrix.elements[14] = -(far + near) / (far - near);
        matrix.elements[15] = 1.0f;

        return matrix;
    }

    public float[] getMatrix() {
        return this.elements;
    }

    public void setIdentity() {
        Arrays.fill(elements, 0.0f);
        elements[0] = 1.0f;
        elements[5] = 1.0f;
        elements[10] = 1.0f;
        elements[15] = 1.0f;
    }

    public void setOrthographic(float left, float right, float bottom, float top, float near, float far) {
        elements[0] = 2.0f / (right - left);
        elements[5] = 2.0f / (top - bottom);
        elements[10] = -2.0f / (far - near);
        elements[12] = -(right + left) / (right - left);
        elements[13] = -(top + bottom) / (top - bottom);
        elements[14] = -(far + near) / (far - near);
        elements[15] = 1.0f;
    }

    public void scale(Vector3 scale) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[0] = scale.x;
        matrix.elements[5] = scale.y;
        matrix.elements[10] = scale.z;

        this.multiply(matrix);
    }

    public void translate(Vector3 translation) {

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[12] = translation.x;
        matrix.elements[13] = translation.y;
        matrix.elements[14] = -translation.z;

        this.multiply(matrix);
    }

    public void rotateX(float angle) {
        float cos = Mathf.cos(angle);
        float sin = Mathf.sin(angle);

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[5] = cos;
        matrix.elements[6] = sin;
        matrix.elements[9] = -sin;
        matrix.elements[10] = cos;

        this.multiply(matrix);


//        float temp1 = elements[4] * cos + elements[8] * sin;
//        float temp2 = elements[5] * cos + elements[9] * sin;
//        float temp3 = elements[6] * cos + elements[10] * sin;
//        float temp4 = elements[7] * cos + elements[11] * sin;
//
//        elements[8] = elements[8] * cos - elements[4] * sin;
//        elements[9] = elements[9] * cos - elements[5] * sin;
//        elements[10] = elements[10] * cos - elements[6] * sin;
//        elements[11] = elements[11] * cos - elements[7] * sin;
//
//        elements[4] = temp1;
//        elements[5] = temp2;
//        elements[6] = temp3;
//        elements[7] = temp4;
    }

    public void rotateY(float angle) {
        float cos = Mathf.cos(angle);
        float sin = Mathf.sin(angle);

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[0] = cos;
        matrix.elements[2] = -sin;
        matrix.elements[8] = sin;
        matrix.elements[10] = cos;

        this.multiply(matrix);

//        float temp1 = elements[0] * cos - elements[8] * sin;
//        float temp2 = elements[1] * cos - elements[9] * sin;
//        float temp3 = elements[2] * cos - elements[10] * sin;
//        float temp4 = elements[3] * cos - elements[11] * sin;
//
//        elements[8] = elements[0] * sin + elements[8] * cos;
//        elements[9] = elements[1] * sin + elements[9] * cos;
//        elements[10] = elements[2] * sin + elements[10] * cos;
//        elements[11] = elements[3] * sin + elements[11] * cos;
//
//        elements[0] = temp1;
//        elements[1] = temp2;
//        elements[2] = temp3;
//        elements[3] = temp4;
    }

    public void rotateZ(float angle) {
        float cos = Mathf.cos(angle);
        float sin = Mathf.sin(angle);

        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();

        matrix.elements[0] = cos;
        matrix.elements[1] = -sin;
        matrix.elements[4] = sin;
        matrix.elements[5] = cos;

        this.multiply(matrix);

//        float temp1 = elements[0] * cos + elements[4] * sin;
//        float temp2 = elements[1] * cos + elements[5] * sin;
//        float temp3 = elements[2] * cos + elements[6] * sin;
//        float temp4 = elements[3] * cos + elements[7] * sin;
//
//        elements[4] = elements[4] * cos - elements[0] * sin;
//        elements[5] = elements[5] * cos - elements[1] * sin;
//        elements[6] = elements[6] * cos - elements[2] * sin;
//        elements[7] = elements[7] * cos - elements[3] * sin;
//
//        elements[0] = temp1;
//        elements[1] = temp2;
//        elements[2] = temp3;
//        elements[3] = temp4;
    }

    public void rotate(Vector3 angles) {
        this.rotateX(angles.x);
        this.rotateY(angles.y);
        this.rotateZ(angles.z);
    }

    public void add(Matrix4f other) {
        for (int i = 0; i < 16; i++) {
            elements[i] += other.elements[i];
        }
    }

    public void subtract(Matrix4f other) {
        for (int i = 0; i < 16; i++) {
            elements[i] -= other.elements[i];
        }
    }

    public void multiply(Matrix4f other) {
        float[] result = new float[16];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float sum = 0.0f;
                for (int k = 0; k < 4; k++) {
                    sum += elements[i * 4 + k] * other.elements[k * 4 + j];
                }
                result[i * 4 + j] = sum;
            }
        }

        elements = result;
    }

    public void divide(Matrix4f other) {
        float[] result = new float[16];

        for (int i = 0; i < 16; i++) {
            if (other.elements[i] != 0.0f) {
                result[i] = elements[i] / other.elements[i];
            } else {
                // Handle division by zero error
                result[i] = 0.0f;
            }
        }

        elements = result;
    }

    public FloatBuffer toFloatBuffer() {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        buffer.put(elements);
        buffer.flip();
        return buffer;
    }

    public static Matrix4f createTransformationMatrix(Vector3 position, Vector3 rotation, Vector3 scale) {
        Matrix4f matrix = new Matrix4f();

        matrix.setIdentity();
        matrix.translate(position);
        matrix.rotateX(rotation.x);
        matrix.rotateY(rotation.y);
        matrix.rotateZ(rotation.z);
        matrix.scale(scale);

        return matrix;
    }

}