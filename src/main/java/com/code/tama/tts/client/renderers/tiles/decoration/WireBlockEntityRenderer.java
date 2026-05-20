/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.renderers.tiles.decoration;

import com.code.tama.tts.core.tileentities.WireBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class WireBlockEntityRenderer implements BlockEntityRenderer<BlockEntity> {

	// How many line segments to subdivide the wire into CoralConsoleTopper more =
	// smoother curve
	private static final int SEGMENTS = 24;

	// Controls the droop. Higher = more sag. A lead uses ~0.05, try 0.5–1.2 for
	// wires.
	private static final float SAG = 2f;

	// Wire thickness in world units (billboard half-width on each side)
	private static final float HALF_WIDTH = 0.025f;

	// Wire color (RGBA 0–255). Change to match your texture/material.
	private static final int R = 40, G = 20, B = 20, A = 255;

	public WireBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
	}

	@Override
	public void render(BlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffers,
			int packedLight, int packedOverlay) {

		if (!(blockEntity instanceof WireBlockEntity be))
			return;
		BlockPos linkedPos = be.getLinkedPos();
		if (linkedPos == null)
			return;

		// Only render from the block with the lower BlockPos to avoid double-draw
		if (!shouldRender(be.getBlockPos(), linkedPos))
			return;

		BlockPos origin = be.getBlockPos();

		// World-space offset from this block's origin to the linked block's origin
		// Add 0.5 to both so the wire connects to block centers
		float dx = (linkedPos.getX() - origin.getX());
		float dy = (linkedPos.getY() - origin.getY());
		float dz = (linkedPos.getZ() - origin.getZ());

		VertexConsumer consumer = buffers.getBuffer(RenderType.leash());

		poseStack.pushPose();
		// The pose stack is already relative to this block entity's position (origin
		// corner).
		// Shift to block center.
		poseStack.translate(0.5, 0.5, 0.5);

		Matrix4f mat = poseStack.last().pose();

		for (int i = 0; i < SEGMENTS; i++) {
			float t0 = (float) i / SEGMENTS;
			float t1 = (float) (i + 1) / SEGMENTS;

			float x0 = dx * t0 + 0.5f; // +0.5 to hit center of linked block
			float y0 = dy * t0 + sagOffset(t0) + 0.5f;
			float z0 = dz * t0 + 0.5f;

			float x1 = dx * t1 + 0.5f;
			float y1 = dy * t1 + sagOffset(t1) + 0.5f;
			float z1 = dz * t1 + 0.5f;

			// Emit a billboard quad between (x0,y0,z0) and (x1,y1,z1).
			// Perpendicular vector in XZ plane (for horizontal thickness).
			float segDx = x1 - x0;
			float segDz = z1 - z0;
			float len = (float) Math.sqrt(segDx * segDx + segDz * segDz);
			float perpX = 0, perpZ = 0;
			if (len > 0.0001f) {
				perpX = -segDz / len * HALF_WIDTH;
				perpZ = segDx / len * HALF_WIDTH;
			}

			// Two quads: one in XZ plane, one in Y for cross-section look
			emitSegment(consumer, mat, x0 - perpX, y0, z0 - perpZ, x0 + perpX, y0, z0 + perpZ, x1 + perpX, y1,
					z1 + perpZ, x1 - perpX, y1, z1 - perpZ, packedLight, packedOverlay);

			emitSegment(consumer, mat, x0, y0 - HALF_WIDTH, z0, x0, y0 + HALF_WIDTH, z0, x1, y1 + HALF_WIDTH, z1, x1,
					y1 - HALF_WIDTH, z1, packedLight, packedOverlay);
		}

		poseStack.popPose();
	}

	/**
	 * Parabolic sag: deepest at t=0.5, zero at both ends. Negative because we want
	 * the wire to droop downward.
	 */
	private float sagOffset(float t) {
		return -SAG * 4.0f * t * (1.0f - t);
	}

	/**
	 * Emit a quad (two triangles) into the vertex consumer.
	 */
	private void emitSegment(VertexConsumer vc, Matrix4f mat, float x0, float y0, float z0, float x1, float y1,
			float z1, float x2, float y2, float z2, float x3, float y3, float z3, int light, int overlay) {
		vc.vertex(mat, x0, y0, z0).color(R, G, B, A).uv(0, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0)
				.endVertex();
		vc.vertex(mat, x1, y1, z1).color(R, G, B, A).uv(1, 0).overlayCoords(overlay).uv2(light).normal(0, 1, 0)
				.endVertex();
		vc.vertex(mat, x2, y2, z2).color(R, G, B, A).uv(1, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0)
				.endVertex();
		vc.vertex(mat, x3, y3, z3).color(R, G, B, A).uv(0, 1).overlayCoords(overlay).uv2(light).normal(0, 1, 0)
				.endVertex();
	}

	/**
	 * Prevents double-rendering: only the block with the lexicographically lower
	 * position renders the wire between a pair.
	 */
	private boolean shouldRender(BlockPos a, BlockPos b) {
		if (a.getX() != b.getX())
			return a.getX() < b.getX();
		if (a.getY() != b.getY())
			return a.getY() < b.getY();
		return a.getZ() < b.getZ();
	}

	// /**
	// * Expand the AABB so the wire doesn't get culled when the anchor block
	// * goes off-screen. Covers the full bounding box of the sagging curve.
	// */
	// @Override
	// public AABB getRenderBoundingBox(BlockEntity blockEntity) {
	// if (!(blockEntity instanceof WireBlockEntity be)) return AABB.INFINITE;
	// BlockPos here = be.getBlockPos();
	// BlockPos there = be.getLinkedPos();
	// if (there == null) return new AABB(here);
	//
	// double minX = Math.min(here.getX(), there.getX()) - 1;
	// double minY = Math.min(here.getY(), there.getY()) - SAG - 1;
	// double minZ = Math.min(here.getZ(), there.getZ()) - 1;
	// double maxX = Math.max(here.getX(), there.getX()) + 2;
	// double maxY = Math.max(here.getY(), there.getY()) + 2;
	// double maxZ = Math.max(here.getZ(), there.getZ()) + 2;
	//
	// return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
	// }
}