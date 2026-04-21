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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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

	@SubscribeEvent
	public static void onWorldRender(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS)
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
		int t = (int) ((mc.level.getGameTime() >> 3) % 6);
		int which = t <= 3 ? t : 6 - t;
		RenderSystem.setShaderTexture(0, RIFT_FRAMES[which]);
		RenderSystem.disableCull();

		cap.GetRiftData().forEach((bp, rift) -> {
			VertexBuffer vbo = riftVBOs.get(rift.getRiftUUID());
			if (vbo == null)
				return;

			poseStack.pushPose();

			// FIX 1: Translate to block position relative to camera FIRST (per frame, not
			// baked)
			// This keeps the actual numbers small, fixing float precision jitter
			// Direction dir = Direction.fromYRot(rift.getYRot());

			float offsetX, offsetZ;

			offsetX = rift.getYRot() == 0 ? 0.002f : rift.getYRot() == 180 ? 0.998f : 0.5f;
			offsetZ = rift.getYRot() == 90 ? 0.998f : rift.getYRot() == 270 ? 0.002f : 0.5f;

			poseStack.translate(bp.getX() + offsetZ - camPos.x, bp.getY() - camPos.y, bp.getZ() + offsetX - camPos.z);

			// FIX 2: Rotate AFTER translate so rotation is around the block's own center
			poseStack.mulPose(Axis.YP.rotationDegrees(rift.getYRot()));

			vbo.bind();
			vbo.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(),
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();

			poseStack.popPose();
		});

		RenderSystem.enableCull();
	}

	private static void buildVBOs(ILevelCap cap, Minecraft mc) {
		cap.GetRiftData().forEach((bp, rift) -> {
			if (riftVBOs.containsKey(rift.getRiftUUID()))
				return;
			assert mc.player != null;
			if (!mc.player.blockPosition().getCenter().closerThan(rift.getPos().getCenter(), 100))
				return;

			// FIX 1: Bake vertices in LOCAL space (centered at origin, small numbers)
			// The world position is handled per-frame in the render matrix above
			BufferBuilder builder = new BufferBuilder(256);
			builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

			// Simple quad centered at origin, 3 wide x 1 tall
			// X: -1.5 to 1.5, Y: 0 to 1, Z: 0 (flat on XY plane, rotation handles facing)
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