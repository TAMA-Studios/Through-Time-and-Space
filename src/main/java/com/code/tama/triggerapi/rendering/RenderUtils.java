/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.AABB;

import com.code.tama.triggerapi.NativeLoader;

/**
 * Rendering math utilities backed by native Rust for batch operations.
 *
 * The pattern here is always: Java handles MC objects and GL calls,
 * Rust handles bulk math on flat float arrays. Never call the native
 * methods for single operations, the JNI overhead isn't worth it.
 * Use them when you have 10+ objects to process per frame.
 */
public class RenderUtils {

    static { NativeLoader.load("tts_native"); }

    // -- Native (Rust) --------------------------------------------------------

    /**
     * Frustum-culls a batch of AABBs against 6 frustum planes.
     *
     * @param aabbs flat array of [minX,minY,minZ,maxX,maxY,maxZ, ...], one entry per AABB
     * @param frustumPlanes 6 planes × 4 floats [nx,ny,nz,d] = 24 floats total
     * @return indices of AABBs that passed (are potentially visible)
     */
    public static native int[] frustumCull(float[] aabbs, float[] frustumPlanes);

    /**
     * Generates all 24 line-segment vertices (bottom face, top face, 4 vertical edges)
     * for N AABBs at once. Output per vertex: [x, y, z, nx, ny, nz], 144 floats per AABB.
     * Feed the result directly into a VertexConsumer; no per-vertex math needed in Java.
     *
     * @param aabbs flat [minX,minY,minZ,maxX,maxY,maxZ, ...]
     */
    public static native float[] buildAABBVertices(float[] aabbs);

    /**
     * Sorts N AABBs by distance from a camera point.
     *
     * @param aabbs flat [minX,minY,minZ,maxX,maxY,maxZ, ...]
     * @param reverse 1 = farthest first (use for transparent geometry), 0 = nearest first
     * @return sorted indices into the original aabbs array
     */
    public static native int[] sortByDistance(float[] aabbs, float cx, float cy, float cz, int reverse);

    /**
     * Packs ARGB float components (each 0.0–1.0) into a single int (0xAARRGGBB).
     * Marginally faster than doing it in Java for tight loops.
     */
    public static native int packARGB(float a, float r, float g, float b);

    /**
     * Transforms N xyz points by a 4×4 column-major matrix (same layout as JOML/GL).
     *
     * @param points flat [x,y,z, x,y,z, ...]
     * @param matrix 16 floats, column-major
     * @return transformed flat [x',y',z', ...]
     */
    public static native float[] transformPoints(float[] points, float[] matrix);

    /**
     * Generates vertices for a wireframe sphere as line segment pairs.
     * Returns flat [x,y,z, x,y,z, ...], each consecutive pair is one line.
     *
     * @param stacks latitude resolution (8–16 is plenty for debug rendering)
     * @param slices longitude resolution
     */
    public static native float[] buildSphereVertices(float cx, float cy, float cz, float radius, int stacks, int slices);

    /**
     * Computes normalised direction vectors for a batch of line segments.
     * Useful when you need to supply normals for RenderType.lines() separately.
     *
     * @param lines flat [x0,y0,z0,x1,y1,z1, ...]
     * @return flat [nx,ny,nz, ...], one normal per line
     */
    public static native float[] computeLineNormals(float[] lines);

    /**
     * Inflates (expands) a batch of AABBs by a fixed amount on all sides.
     *
     * @param aabbs flat [minX,minY,minZ,maxX,maxY,maxZ, ...]
     * @param amount how much to expand on each side (negative = shrink)
     */
    public static native float[] inflateAABBs(float[] aabbs, float amount);

    // -- Java helpers, convenience wrappers that do the MC↔float[] conversion --

    /**
     * Packs a list of AABBs into the flat float array format Rust expects.
     * Call once, reuse the result across frustumCull / buildAABBVertices / sortByDistance.
     */
    public static float[] packAABBs(AABB... boxes) {
        float[] out = new float[boxes.length * 6];
        for (int i = 0; i < boxes.length; i++) {
            AABB b = boxes[i];
            int base = i * 6;
            out[base]   = (float) b.minX;  out[base+1] = (float) b.minY;  out[base+2] = (float) b.minZ;
            out[base+3] = (float) b.maxX;  out[base+4] = (float) b.maxY;  out[base+5] = (float) b.maxZ;
        }
        return out;
    }

    /**
     * Submits pre-built AABB vertices (from buildAABBVertices) to a VertexConsumer.
     * The vertex data already has normals baked in, just pump them through.
     *
     * @param vertexData output of buildAABBVertices()
     * @param pose       current PoseStack.last()
     * @param consumer   the VertexConsumer for RenderType.lines()
     * @param r,g,b,a    line colour
     */
    public static void submitAABBVertices(
            float[] vertexData,
            PoseStack.Pose pose,
            VertexConsumer consumer,
            float r, float g, float b, float a) {

        // Each vertex is 6 floats: [x, y, z, nx, ny, nz]
        for (int i = 0; i < vertexData.length; i += 6) {
            consumer.vertex(pose.pose(),   vertexData[i],   vertexData[i+1], vertexData[i+2])
                    .color(r, g, b, a)
                    .normal(pose.normal(), vertexData[i+3], vertexData[i+4], vertexData[i+5])
                    .endVertex();
        }
    }

    /**
     * Submits sphere vertices (from buildSphereVertices) to a VertexConsumer.
     * Computes normals on the fly since spheres need them per-segment.
     */
    public static void submitSphereVertices(
            float[] vertexData,
            PoseStack.Pose pose,
            VertexConsumer consumer,
            float r, float g, float b, float a) {

        float[] normals = computeLineNormals(vertexData);

        // vertex data: [x0,y0,z0, x1,y1,z1, ...], pairs of endpoints
        // normals:     [nx,ny,nz, ...], one per pair
        for (int i = 0, n = 0; i < vertexData.length; i += 6, n += 3) {
            float nx = normals[n], ny = normals[n+1], nz = normals[n+2];
            consumer.vertex(pose.pose(), vertexData[i],   vertexData[i+1], vertexData[i+2])
                    .color(r, g, b, a)
                    .normal(pose.normal(), nx, ny, nz)
                    .endVertex();
            consumer.vertex(pose.pose(), vertexData[i+3], vertexData[i+4], vertexData[i+5])
                    .color(r, g, b, a)
                    .normal(pose.normal(), nx, ny, nz)
                    .endVertex();
        }
    }

    // -- Usage example ---------------------------------------------------------
    //
    // Rendering 500 entity hitboxes each frame, old way (all in Java):
    //
    //   for (Entity e : entities) {
    //       renderAABBLines(poseStack, consumer, e.getBoundingBox(), r, g, b, a);
    //   }
    //
    // New way, Rust does the bulk math, Java just submits to the GPU:
    //
    //   // Pack all AABBs once
    //   AABB[] boxes = entities.stream().map(Entity::getBoundingBox).toArray(AABB[]::new);
    //   float[] packed = RenderUtils.packAABBs(boxes);
    //
    //   // Optional: cull against frustum first
    //   int[] visible = RenderUtils.frustumCull(packed, frustumPlanes);
    //   float[] culledPacked = filterByIndices(packed, visible); // helper below
    //
    //   // Optional: sort back-to-front for transparent lines
    //   // int[] sorted = RenderUtils.sortByDistance(culledPacked, camX, camY, camZ, 1);
    //
    //   // Build all vertices in one native call
    //   float[] verts = RenderUtils.buildAABBVertices(culledPacked);
    //
    //   // Submit to GPU, just buffer writes, no more per-edge sqrt
    //   RenderUtils.submitAABBVertices(verts, poseStack.last(), consumer, r, g, b, a);

    /** Filters a packed AABB array down to only the indices returned by frustumCull. */
    public static float[] filterByIndices(float[] aabbs, int[] indices) {
        float[] out = new float[indices.length * 6];
        for (int i = 0; i < indices.length; i++) {
            System.arraycopy(aabbs, indices[i] * 6, out, i * 6, 6);
        }
        return out;
    }
}