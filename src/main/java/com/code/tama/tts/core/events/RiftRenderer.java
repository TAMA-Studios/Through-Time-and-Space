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

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.code.tama.triggerapi.helpers.rendering.FBOHelper;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;

@Mod.EventBusSubscriber(modid = TTSMod.MODID)
public class RiftRenderer {
	private static final Map<UUID, VertexBuffer> riftVBOs = new HashMap<>();
	private static final ResourceLocation RIFT_FRAMES[] = new ResourceLocation[]{
			new ResourceLocation(TTSMod.MODID, "textures/environment/rift/frame1.png"),
			new ResourceLocation(TTSMod.MODID, "textures/environment/rift/frame2.png"),
			new ResourceLocation(TTSMod.MODID, "textures/environment/rift/frame3.png"),
			new ResourceLocation(TTSMod.MODID, "textures/environment/rift/frame4.png")};

	// Call this whenever rift data changes (on sync packet receipt)
	public static void markDirty() {
		riftVBOs.values().forEach(VertexBuffer::close);
		riftVBOs.clear();
	}

	static Creeper creeper;

	@SubscribeEvent
	public static void onWorldRender(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
			return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null || mc.player == null)
			return;

		Camera camera = mc.gameRenderer.getMainCamera();
		Vec3 camPos = camera.getPosition();

		ILevelCap cap = mc.level.getCapability(Capabilities.LEVEL_CAPABILITY).orElse(new LevelCapability(mc.level));

		buildVBOs(cap, mc);

		PoseStack poseStack = event.getPoseStack();

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		// int t = (int) ((mc.level.getGameTime() >> 3) % 6);
		// int which = t <= 3 ? t : 6 - t;
		// RenderSystem.setShaderTexture(0, RIFT_FRAMES[which]);

		int t = (int) (mc.level.getGameTime() & 127) + 128;

		// System.out.println(t / 255f);
		RenderSystem.setShaderColor((t >> 3) / 255f, (t >> 2) / 255f, t / 255f, 1f);

		RenderSystem.disableCull();

		final int c[] = {0};
		cap.GetRiftData().forEach((bp, rift) -> {
			int which = rift.getUsedTime() / 8;
			RenderSystem.setShaderTexture(0, RIFT_FRAMES[which]);

			VertexBuffer vbo = riftVBOs.get(rift.getRiftUUID());
			if (vbo == null)
				return;

			poseStack.pushPose();

			float offsetX, offsetZ;

			offsetX = rift.getYRot() == 0 ? 0.002f : rift.getYRot() == 180 ? 0.998f : 0.5f;
			offsetZ = rift.getYRot() == 90 ? 0.998f : rift.getYRot() == 270 ? 0.002f : 0.5f;

			poseStack.translate(bp.getX() + offsetZ - camPos.x, bp.getY() - camPos.y, bp.getZ() + offsetX - camPos.z);

			poseStack.mulPose(Axis.YP.rotationDegrees(rift.getYRot()));

			if (creeper == null)
				creeper = new Creeper(EntityType.CREEPER, mc.level);

			if (rift.fboHelper == null)
				rift.fboHelper = new FBOHelper();

			rift.fboHelper.Render(poseStack, (poseStack1, buffer) -> {
				poseStack1.pushPose();
				if (which == 3) {
					poseStack.translate(0, -0.5, 0);
					poseStack1.scale(1, 2, 1);
				}

				vbo.bind();
				vbo.drawWithShader(poseStack1.last().pose(), RenderSystem.getProjectionMatrix(),
						Objects.requireNonNull(RenderSystem.getShader()));
				VertexBuffer.unbind();
				buffer.endBatch();
				poseStack1.popPose();
			}, (poseStack1, buffer) -> {
			}, (poseStack1, buffer) -> {
				poseStack1.pushPose();
				StencilUtils.drawColoredCube(poseStack1, 4, new Vec3(255, 255, 255));
				buffer.endBatch();

				poseStack1.translate(0, -0.5, -2f);

				rift.WhatsInside().render.accept(rift, poseStack1, buffer);
				buffer.endBatch();
				poseStack1.popPose();
			});
			//
			poseStack.popPose();
		});

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		RenderSystem.enableCull();
	}

	private static void buildVBOs(ILevelCap cap, Minecraft mc) {
		cap.GetRiftData().forEach((bp, rift) -> {
			if (riftVBOs.containsKey(rift.getRiftUUID()))
				return;
			assert mc.player != null;
			if (!mc.player.blockPosition().getCenter().closerThan(rift.getPos().getCenter(), 100))
				return;

			BufferBuilder builder = new BufferBuilder(256);
			builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

			builder.vertex(-1.5f, 1.0f, 0).uv(0, 0).endVertex();
			builder.vertex(1.5f, 1.0f, 0).uv(1, 0).endVertex();
			builder.vertex(1.5f, 0.0f, 0).uv(1, 1).endVertex();
			builder.vertex(-1.5f, 0.0f, 0).uv(0, 1).endVertex();

			BufferBuilder.RenderedBuffer rendered = builder.end();

			VertexBuffer vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
			vbo.bind();
			vbo.upload(rendered);
			VertexBuffer.unbind();

			riftVBOs.put(rift.getRiftUUID(), vbo);
		});
	}
}