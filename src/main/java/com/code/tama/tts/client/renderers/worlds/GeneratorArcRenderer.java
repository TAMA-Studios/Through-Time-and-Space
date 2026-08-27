/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.renderers.worlds;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.TRenderTypes;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = TTSMod.MODID)
public class GeneratorArcRenderer {
	public static Set<BlockPos> generators = null;

	private static final double RANGE = 20.0;
	private static final double RANGE_SQ = RANGE * RANGE;
	private static final float WIDTH = 0.08f;
	private static final int COLOR = 0x66CCFF;

	@SubscribeEvent
	public static void onRenderLevel(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
			return;

		Level level = Minecraft.getInstance().level;
		if (level == null)
			return;

		if (generators == null || level.getGameTime() % 80 == 1) {
			TARDISLevelCapability.GetClientTARDISCapSupplier().ifPresent(c -> {
				Set<BlockPos> fresh = new HashSet<>();
				c.GetData().getSubSystemsData().DynamorphicGeneratorStacks.forEach(s -> {
					fresh.add(s.getBlockPos());
				});

				generators = fresh;
			});
		}

		if (generators == null)
			return;

		if (generators.size() < 2)
			return;

		Vec3 camPos = event.getCamera().getPosition();
		PoseStack poseStack = event.getPoseStack();

		MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
		VertexConsumer vc = buffer.getBuffer(TRenderTypes.ARC);

		poseStack.pushPose();
		poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

		List<BlockPos> list = new ArrayList<>(generators);
		long time = level.getGameTime();
		Vec3 viewVec = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector() == null
				? Vec3.ZERO
				: new Vec3(event.getCamera().getLookVector().x(), event.getCamera().getLookVector().y(),
						event.getCamera().getLookVector().z());

		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				BlockPos a = list.get(i);
				BlockPos b = list.get(j);
				double distSq = a.distSqr(b);
				if (distSq <= RANGE_SQ) {
					drawArc(vc, poseStack.last(), a, b, time, (float) Math.sqrt(distSq), viewVec);
				}
			}
		}

		poseStack.popPose();
		buffer.endBatch(TRenderTypes.ARC);
	}

	private static void drawArc(VertexConsumer vc, PoseStack.Pose pose, BlockPos a, BlockPos b, long time,
			float distance, Vec3 viewVec) {
		Vec3 start = Vec3.atCenterOf(a);
		Vec3 end = Vec3.atCenterOf(b);
		Vec3 dir = end.subtract(start);

		Vec3 perp1 = dir.cross(new Vec3(0, 1, 0));
		if (perp1.lengthSqr() < 1e-4)
			perp1 = dir.cross(new Vec3(1, 0, 0));
		perp1 = perp1.normalize();
		Vec3 perp2 = dir.cross(perp1).normalize();

		int segments = Mth.clamp((int) (distance * 1.5f), 6, 32);

		// Reseed every few ticks so the bolt "flickers" instead of staying static
		long seed = a.asLong() * 341873128712L + b.asLong() * 132897987541L + (time / 3);
		Random rand = new Random(seed);

		Vec3[] points = new Vec3[segments + 1];
		points[0] = start;
		points[segments] = end;
		for (int i = 1; i < segments; i++) {
			float t = (float) i / segments;
			Vec3 base = start.lerp(end, t);
			float jitter = 0.35f * (1 - Math.abs(t - 0.5f) * 2f) + 0.04f; // taper at both ends
			double o1 = (rand.nextDouble() - 0.5) * jitter;
			double o2 = (rand.nextDouble() - 0.5) * jitter;
			points[i] = base.add(perp1.scale(o1)).add(perp2.scale(o2));
		}

		int r = (COLOR >> 16) & 0xFF, g = (COLOR >> 8) & 0xFF, bC = COLOR & 0xFF, alpha = 220;
		float scroll = (time % 40) / 40f; // animate texture flow

		for (int i = 0; i < segments; i++) {
			Vec3 p0 = points[i];
			Vec3 p1 = points[i + 1];
			Vec3 segDir = p1.subtract(p0);
			if (segDir.lengthSqr() < 1e-7)
				continue;

			Vec3 widthDir = segDir.cross(viewVec);
			if (widthDir.lengthSqr() < 1e-7)
				widthDir = perp1;
			widthDir = widthDir.normalize().scale(WIDTH);

			float u0 = (float) i / segments + scroll;
			float u1 = (float) (i + 1) / segments + scroll;

			vertex(vc, pose, p0.subtract(widthDir), u0, 0, r, g, bC, alpha);
			vertex(vc, pose, p0.add(widthDir), u0, 1, r, g, bC, alpha);
			vertex(vc, pose, p1.add(widthDir), u1, 1, r, g, bC, alpha);
			vertex(vc, pose, p1.subtract(widthDir), u1, 0, r, g, bC, alpha);
		}
	}

	private static void vertex(VertexConsumer vc, PoseStack.Pose pose, Vec3 pos, float u, float v, int r, int g, int b,
			int a) {
		vc.vertex(pose.pose(), (float) pos.x, (float) pos.y, (float) pos.z).color(r, g, b, a).uv(u, v).uv2(15728880) // full-bright
																														// lightmap
																														// so
																														// it
																														// glows
																														// regardless
																														// of
																														// local
																														// light
				.endVertex();
	}
}