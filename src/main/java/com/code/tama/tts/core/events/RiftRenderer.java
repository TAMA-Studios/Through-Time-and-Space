/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.events;

import java.util.*;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.capabilities.caps.LevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ILevelCap;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.joml.Matrix4f;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = TTSMod.MODID)
public class RiftRenderer {
	private static Map<UUID, VertexBuffer> riftVBOs = new HashMap<>();
	private static boolean dirty = true;
	private static ILevelCap cap;

	@SubscribeEvent
	public static void onWorldRender(RenderLevelStageEvent event) {
		Minecraft mc = Minecraft.getInstance();
		Camera camera = mc.gameRenderer.getMainCamera();
		Vec3 camPos = camera.getPosition();

		if (mc.level == null)
			return;

		cap = mc.level.getCapability(Capabilities.LEVEL_CAPABILITY).orElse(new LevelCapability(mc.level));

		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
			return;

		riftVBOs = new HashMap<>();
		buildVBOs(mc, camPos);
		riftVBOs.forEach((uuid, vbo) -> {
			if (vbo == null || dirty) {
				buildVBOs(mc, camPos);
				dirty = false;
			}

			if (vbo == null)
				return;

			PoseStack poseStack = event.getPoseStack();
			poseStack.pushPose();

			poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, new ResourceLocation(TTSMod.MODID, "textures/environment/rift.png"));

			RenderSystem.disableCull();

			vbo.bind();
			vbo.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
			VertexBuffer.unbind();

			RenderSystem.enableCull();

			poseStack.popPose();
		});
	}

	private static void buildVBOs(Minecraft mc, Vec3 camPos) {
		cap.GetRiftData().forEach((bp, rift) -> {
			if (riftVBOs.containsKey(rift.getRiftUUID()))
				return;
			if (!mc.player.blockPosition().getCenter().closerThan(rift.getPos().getCenter(), 100))
				return;

			BufferBuilder builder = new BufferBuilder(256);
			builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

			PoseStack stack = new PoseStack();
			stack.pushPose();

			stack.mulPose(Axis.YP.rotationDegrees(rift.getYRot()));
			stack.translate(bp.getX() + 0.5, bp.getY(), bp.getZ());

			// Apply the small Z-fighting offset in local (rotated) space
			float offset = 0.001f;
			switch ((int) rift.getYRot()) {
				case 0 :
					stack.translate(0, 0, offset);
					break;
				case 90 :
					stack.translate(-offset, 0, 0);
					break;
				case 180 :
					stack.translate(0, 0, -offset);
					break;
				case 270 :
					stack.translate(offset, 0, 0);
					break;
			}

			Matrix4f matrix = stack.last().pose();

			builder.vertex(matrix, -1.5f, 1, 0).uv(0, 0).endVertex();
			builder.vertex(matrix, 1.5f, 1, 0).uv(1, 0).endVertex();
			builder.vertex(matrix, 1.5f, 0, 0).uv(1, 1).endVertex();
			builder.vertex(matrix, -1.5f, 0, 0).uv(0, 1).endVertex();

			BufferBuilder.RenderedBuffer rendered = builder.end();

			VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
			vbo.bind();
			vbo.upload(rendered);
			VertexBuffer.unbind();

			stack.popPose();

			riftVBOs.put(rift.getRiftUUID(), vbo);
		});
	}
}
