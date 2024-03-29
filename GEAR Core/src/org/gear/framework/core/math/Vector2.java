package org.gear.framework.core.math;

public class Vector2 {

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public static Vector2 zero() {
        return new Vector2(0f, 0f);
    }

    public static Vector2 normalize(Vector2 vector) {

        float x = 0, y = 0;
        float length = length(vector);

        if (length != 0.0f) {
            x = vector.x / length;
            y = vector.y / length;
        }

        return new Vector2(x, y);
    }

    public static float length(Vector2 vector) {
        return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        return new Vector2(a.x + b.x, a.y + b.y);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    public static Vector2 multiply(Vector2 vector, float scalar) {
        return new Vector2(vector.x * scalar, vector.y * scalar);
    }

    public static Vector2 divide(Vector2 vector, float scalar) {

        if (scalar != 0.0f) {
            return new Vector2(vector.x / scalar, vector.y / scalar);
        } else {
            return zero();
        }
    }

    public static Vector2 left() {
        return new Vector2(-1f, 0f);
    }

    public static Vector2 right() {
        return new Vector2(1f, 0f);
    }

    public static Vector2 up() {
        return new Vector2(0f, 1f);
    }

    public static Vector2 down() {
        return new Vector2(0f, -1f);
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
