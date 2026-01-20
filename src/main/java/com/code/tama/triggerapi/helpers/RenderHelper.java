/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.helpers;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.registries.forge.TTSItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import lombok.Getter;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.code.tama.triggerapi.boti.client.BotiBlockContainer;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = TTSMod.MODID)
public class RenderHelper {
	private static VertexBuffer vbo;
	private static boolean dirty = true;

	@Getter
	private static final List<BotiBlockContainer> blocks = new ArrayList<>();

	@SubscribeEvent
	public static void onWorldRender(RenderLevelStageEvent event) {
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS)
			return;

		Minecraft mc = Minecraft.getInstance();
		if (mc.level == null)
			return;

		if (!mc.player.getInventory().getArmor(3).getItem().equals(TTSItems.HOLO_GLASSES.get()))
			return;

		Camera camera = mc.gameRenderer.getMainCamera();
		Vec3 camPos = camera.getPosition();

		if (vbo == null || dirty) {
			rebuildVBO(mc, camPos);
			dirty = false;
		}

		if (vbo == null)
			return;

		PoseStack poseStack = event.getPoseStack();
		poseStack.pushPose();

		// IMPORTANT: VBO already contains world-space vertices.
		// We translate by negative camera position once.
		poseStack.translate(-camPos.x, -camPos.y, -camPos.z);

		RenderSystem.setShader(GameRenderer::getPositionColorTexLightmapShader);
		RenderSystem.setShaderColor(0.5f, 0.5f, 1f, 0.5f);

		vbo.bind();
		vbo.drawWithShader(poseStack.last().pose(), RenderSystem.getProjectionMatrix(), RenderSystem.getShader());
		VertexBuffer.unbind();

		poseStack.popPose();
	}

	private static void rebuildVBO(Minecraft mc, Vec3 camPos) {
		if (vbo != null) {
			vbo.close();
		}

		vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);

		BufferBuilder builder = Tesselator.getInstance().getBuilder();
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);

		PoseStack stack = new PoseStack();

		for (BotiBlockContainer container : blocks) {
			stack.pushPose();
			BlockPos pos = container.getPos();

			// Build vertices in WORLD SPACE
			stack.translate(pos.getX(), pos.getY(), pos.getZ());

			mc.getBlockRenderer().renderBatched(container.getState(), pos, mc.level, stack, builder, false,
					mc.level.random);

			stack.popPose();
		}

		BufferBuilder.RenderedBuffer rendered = builder.end();

		vbo.bind();
		vbo.upload(rendered);
		VertexBuffer.unbind();
	}

	public static int addBlockToRender(BlockPos pos, BlockState state) {
		int id = blocks.size();
		blocks.add(new BotiBlockContainer(Minecraft.getInstance().level, LightTexture.FULL_BRIGHT, pos, state));
		dirty = true;
		return id;
	}

	public static void removeBlock(BlockPos pos) {
		blocks.removeIf(block -> block.getPos().equals(pos));
		dirty = true;
	}
}
