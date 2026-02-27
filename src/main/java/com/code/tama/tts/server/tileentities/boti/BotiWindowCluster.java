/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities.boti;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

public class BotiWindowCluster {

    // -------------------------------------------------------------------------
    // Cluster discovery
    // -------------------------------------------------------------------------

    /** Flood-fill all orthogonally connected BotiWindowTile blocks from the seed. */
    public static List<BlockPos> findCluster(Level level, BlockPos seed) {
        List<BlockPos> result = new ArrayList<>();
        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> queue = new ArrayDeque<>();
        queue.add(seed);
        visited.add(seed);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();
            result.add(current);
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (!visited.contains(neighbor) && level.getBlockEntity(neighbor) instanceof BotiWindowTile) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Master election — block closest to geometric center, tie-break: min X, then Y, then Z
    // -------------------------------------------------------------------------

    public static BlockPos electMaster(List<BlockPos> cluster) {
        if (cluster.isEmpty()) throw new IllegalArgumentException("Empty cluster");
        if (cluster.size() == 1) return cluster.get(0);

        // Compute float center
        double cx = 0, cy = 0, cz = 0;
        for (BlockPos p : cluster) { cx += p.getX(); cy += p.getY(); cz += p.getZ(); }
        cx /= cluster.size(); cy /= cluster.size(); cz /= cluster.size();

        final double fcx = cx, fcy = cy, fcz = cz;

        return cluster.stream().min(Comparator
                .comparingDouble((BlockPos p) -> distSq(p, fcx, fcy, fcz))
                .thenComparingInt(BlockPos::getX)
                .thenComparingInt(BlockPos::getY)
                .thenComparingInt(BlockPos::getZ)
        ).orElseThrow();
    }

    private static double distSq(BlockPos p, double cx, double cy, double cz) {
        double dx = p.getX() - cx, dy = p.getY() - cy, dz = p.getZ() - cz;
        return dx*dx + dy*dy + dz*dz;
    }

    // -------------------------------------------------------------------------
    // Stencil VBO builder (client side)
    // Build a VBO of cube quads around every block in the cluster.
    // Each face is only emitted if the neighboring block is NOT also in the cluster,
    // so interior shared faces are culled.
    // -------------------------------------------------------------------------

    @OnlyIn(Dist.CLIENT)
    public static VertexBuffer buildStencilVBO(List<BlockPos> cluster) {
        Set<BlockPos> clusterSet = new HashSet<>(cluster);

        BufferBuilder buffer = new BufferBuilder(cluster.size() * 6 * 4 * 8);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (BlockPos pos : cluster) {
            float x0 = pos.getX(), y0 = pos.getY(), z0 = pos.getZ();
            float x1 = x0 + 1, y1 = y0 + 1, z1 = z0 + 1;

            for (Direction face : Direction.values()) {
                // Cull faces shared with another cluster member
                if (clusterSet.contains(pos.relative(face))) continue;

                emitFace(buffer, face, x0, y0, z0, x1, y1, z1);
            }
        }

        BufferBuilder.RenderedBuffer rendered = buffer.end();
        VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
        vbo.bind();
        vbo.upload(rendered);
        VertexBuffer.unbind();
        return vbo;
    }

    @OnlyIn(Dist.CLIENT)
    private static void emitFace(BufferBuilder buf, Direction face,
                                  float x0, float y0, float z0,
                                  float x1, float y1, float z1) {
        switch (face) {
            case UP ->    { buf.vertex(x0,y1,z0).endVertex(); buf.vertex(x0,y1,z1).endVertex(); buf.vertex(x1,y1,z1).endVertex(); buf.vertex(x1,y1,z0).endVertex(); }
            case DOWN ->  { buf.vertex(x0,y0,z1).endVertex(); buf.vertex(x0,y0,z0).endVertex(); buf.vertex(x1,y0,z0).endVertex(); buf.vertex(x1,y0,z1).endVertex(); }
            case NORTH -> { buf.vertex(x1,y0,z0).endVertex(); buf.vertex(x0,y0,z0).endVertex(); buf.vertex(x0,y1,z0).endVertex(); buf.vertex(x1,y1,z0).endVertex(); }
            case SOUTH -> { buf.vertex(x0,y0,z1).endVertex(); buf.vertex(x1,y0,z1).endVertex(); buf.vertex(x1,y1,z1).endVertex(); buf.vertex(x0,y1,z1).endVertex(); }
            case WEST ->  { buf.vertex(x0,y0,z0).endVertex(); buf.vertex(x0,y0,z1).endVertex(); buf.vertex(x0,y1,z1).endVertex(); buf.vertex(x0,y1,z0).endVertex(); }
            case EAST ->  { buf.vertex(x1,y0,z1).endVertex(); buf.vertex(x1,y0,z0).endVertex(); buf.vertex(x1,y1,z0).endVertex(); buf.vertex(x1,y1,z1).endVertex(); }
        }
    }
}