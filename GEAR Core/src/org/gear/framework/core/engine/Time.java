package org.gear.framework.core.engine;

public class Time {
    private static final Time instance = new Time();
    private float deltaTime;
    private long currentTime;
    private long previousTime;

    private Time() {
    }

    public static Time getInstance() {
        return instance;
    }

    public static void update() {
        long newTime = System.nanoTime();
        float elapsedTime = (newTime - instance.previousTime) / 1e9f;
        instance.setDeltaTime(elapsedTime);
        instance.setCurrentTime(newTime);
        instance.previousTime = newTime;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    private void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }

    private void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public static float deltaTime() {
        return instance.deltaTime;
    }

    public static float fps() {
        return 1f / instance.deltaTime;
    }

    public static float time() {
        return instance.currentTime / 1e9f;
    }

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}