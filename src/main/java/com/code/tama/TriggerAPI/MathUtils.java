package com.code.tama.TriggerAPI;

import net.minecraft.world.phys.Vec3;

public class MathUtils {
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float lerp(float start, float end, float t) {
        return start + t * (end - start);
    }

    public static double angleBetween(Vec3 vec1, Vec3 vec2) {
        double dot = vec1.dot(vec2);
        double mag = vec1.length() * vec2.length();
        return Math.toDegrees(Math.acos(dot / mag));
    }
}