/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public class FluidQuadCollector implements VertexConsumer {

    public static class FluidVertex {
        public float x, y, z;
        public float r, g, b, a;
        public float u, v;
        public int light;
    }

    @Getter
    private final List<FluidVertex> vertices = new ArrayList<>();

    private FluidVertex current;

    // ---- VertexConsumer methods ---- //

    @Override
    public @NotNull VertexConsumer vertex(double x, double y, double z) {
        current = new FluidVertex();
        current.x = (float) x;
        current.y = (float) y;
        current.z = (float) z;
        return this;
    }

    @Override
    public @NotNull VertexConsumer color(int r, int g, int b, int a) {
        if (current != null) {
            current.r = r / 255f;
            current.g = g / 255f;
            current.b = b / 255f;
            current.a = a / 255f;
        }
        return this;
    }

    @Override
    public @NotNull VertexConsumer uv(float u, float v) {
        if (current != null) {
            current.u = u;
            current.v = v;
        }
        return this;
    }

    @Override
    public @NotNull VertexConsumer overlayCoords(int u, int v) {
        // Fluids don’t use overlay
        return this;
    }

    @Override
    public @NotNull VertexConsumer uv2(int u, int v) {
        // Convert 2 ints into packed light
        if (current != null) {
            current.light = (v << 16) | (u & 0xFFFF);
        }
        return this;
    }

    @Override
    public @NotNull VertexConsumer uv2(int packedLight) {
        if (current != null) {
            current.light = packedLight;
        }
        return this;
    }

    @Override
    public @NotNull VertexConsumer normal(float nx, float ny, float nz) {
        // Fluids don’t provide normals
        return this;
    }

    @Override
    public void endVertex() {
        if (current != null) {
            vertices.add(current);
            current = null;
        }
    }

    @Override
    public void defaultColor(int r, int g, int b, int a) {}

    @Override
    public void unsetDefaultColor() {}
}
