/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers.rendering;

import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.boti.IHelpWithFBOs;
import com.code.tama.triggerapi.boti.client.BotiPortalModel;
import com.code.tama.tts.TTSConfig;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.mixin.client.IMinecraftAccessor;
import com.code.tama.tts.mixin.client.RenderStateShardAccessor;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import java.util.function.BiConsumer;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;

// Big thanks to Jeryn for helping with this
public class FBOHelper {

	public static final ResourceLocation BLACK = new ResourceLocation(TTSMod.MODID, "textures/black.png"); // TODO: set
																											// RGB
																											// values
																											// when
																											// rendering
																											// this for
																											// sky color

	public static StencilBufferStorage stencilBufferStorage = new StencilBufferStorage();
	public RenderTarget renderTarget;

	public FBOHelper() {
		this.start();
	}

	public static void copyColor(RenderTarget src, RenderTarget dest) {
		GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
		GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
		GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height,
				GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
	}

	public static void copyDepth(RenderTarget src, RenderTarget dest) {
		GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
		GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
		GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height,
				GlConst.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT, GlConst.GL_NEAREST);
	}

	public static void copyRenderTarget(RenderTarget src, RenderTarget dest) {
		GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
		GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
		GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height,
				GlConst.GL_DEPTH_BUFFER_BIT | GlConst.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT,
				GlConst.GL_NEAREST);
	}

	public static void setRenderTargetColor(RenderTarget src, float r, float g, float b, float a) {
		src.setClearColor(r, g, b, a);
	}

	public void Render(AbstractPortalTile blockEntity, PoseStack stack, int packedLight) {
		if (!TTSConfig.ClientConfig.BOTI_ENABLED.get())
			return;
		RenderTarget mainTarget = Minecraft.getInstance().getMainRenderTarget();

		if (ModList.get().isLoaded("immersive_portals")) {
			return; // Don't even risk it
		}

		if (!((IHelpWithFBOs) mainTarget).tts$IsStencilBufferEnabled())
			((IHelpWithFBOs) mainTarget).tts$SetStencilBufferEnabled(true);

		stack.pushPose();

		stack.translate(0, 0, -0.5);

		mainTarget.unbindWrite();

		start();

		copyRenderTarget(mainTarget, renderTarget);

		MultiBufferSource.BufferSource botiBuffer = stencilBufferStorage.getConsumer();
		// TODO: Render Door Frame RIGHT HERE (implement datapack door frames, BOTI mask
		// named "BOTI")

		// botiBuffer.endBatch();

		// Enable and configure stencil buffer
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

		RenderSystem.depthMask(true);

		// Render mask
		stack.pushPose();

		// TODO: datapack door frame stencil here
		// Render Stencil
		GL11.glColorMask(false, false, false, false);
		BotiPortalModel.createBodyLayer().bakeRoot().render(stack, botiBuffer.getBuffer(RenderType.solid()),
				packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
		botiBuffer.endBatch();
		stack.popPose();

		// Backup depth by copying to BOTI FBO
		copyDepth(renderTarget, mainTarget);
		renderTarget.bindWrite(false);
		// Clear main buffer depth
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		// Enable stencil
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

		GL11.glColorMask(true, true, true, false);

		// Render Mask
		stack.pushPose();
		stack.translate(0, 0.5, 0);
		RenderSystem.enableCull();

		// TODO: SKY RENDERER!!!
		// Sets the sky color every 30 seconds/skycolor being null
		if (blockEntity.SkyColor == null
				|| (Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 1)
						% 600 == 0) {
			if (blockEntity.type != null) {
				Minecraft mc = Minecraft.getInstance();

				ClientLevel oldLevel = mc.level;
				// LevelRenderer oldRenderer = mc.levelRenderer;

				assert mc.level != null;
				Holder<DimensionType> dimType = mc.level.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE)
						.getHolderOrThrow(blockEntity.dimensionTypeId);

				LevelRenderer renderer = new LevelRenderer(mc, mc.getEntityRenderDispatcher(),
						mc.getBlockEntityRenderDispatcher(), mc.renderBuffers());
				ClientLevel level = new ClientLevel(mc.player.connection, mc.level.getLevelData(),
						blockEntity.targetLevel, dimType, mc.options.getEffectiveRenderDistance(),
						mc.options.getEffectiveRenderDistance(), mc.level.getProfilerSupplier(), renderer, false, 0);
				renderer.setLevel(level);

				mc.level = level;
				// ((IMinecraftAccessor) mc).setLevelRenderer(renderer);

				// mc.levelRenderer.renderLevel(stack, 0f, 0, false,
				// mc.gameRenderer.getMainCamera(),
				// mc.gameRenderer, mc.gameRenderer.lightTexture(), stack.last().pose());

				blockEntity.SkyColor = Minecraft.getInstance().level.getSkyColor(blockEntity.targetPos.getCenter(),
						((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);

				// mc.levelRenderer.renderSky(stack, stack.last().pose(), 0f,
				// mc.gameRenderer.getMainCamera(), true, () -> {});
				// mc.levelRenderer.renderClouds(stack, stack.last().pose(), 0f, 0, 0, 0);

				mc.level = oldLevel;
				// ((IMinecraftAccessor) mc).setLevelRenderer(oldRenderer);
			} else
				blockEntity.SkyColor = Minecraft.getInstance().level.getSkyColor(
						Minecraft.getInstance().player.position(),
						((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
			// BOTI.setRenderTargetColor(FBO.renderTarget, (float) skyColor.x, (float)
			// skyColor.y, (float)
			// skyColor.z, 1);
		}
		StencilUtils.drawColoredFrame(stack, 1, 2, blockEntity.SkyColor);
		botiBuffer.endBatch();
		stack.popPose();

		stack.pushPose();
		// stack.scale(10, 10, 10);

		// Render BOTI Scene
		BOTIUtils.RenderScene(stack, blockEntity);
		RenderSystem.disableCull();
		botiBuffer.endBatch();

		stack.popPose();

		GL11.glColorMask(true, true, true, true);

		// Set VBO back to main
		mainTarget.bindWrite(false);

		// Restore depth by copying from BOTI FBO back to Main
		copyColor(renderTarget, mainTarget);

		// Disable Stencil
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);

		// Set VBO to main and [insert something here] viewport
		mainTarget.bindWrite(true);

		// Copy the color from BOTI FBO to Main target
		copyColor(renderTarget, mainTarget);

		GL11.glDisable(GL11.GL_STENCIL_TEST);

		RenderSystem.depthMask(true);

		stack.popPose();
	}

	public void Render(PoseStack stack, BiConsumer<PoseStack, MultiBufferSource.BufferSource> drawStencil,
			BiConsumer<PoseStack, MultiBufferSource.BufferSource> drawFrame,
			BiConsumer<PoseStack, MultiBufferSource.BufferSource> drawScene) {
		RenderTarget mainTarget = Minecraft.getInstance().getMainRenderTarget();

		if (!((IHelpWithFBOs) mainTarget).tts$IsStencilBufferEnabled())
			((IHelpWithFBOs) mainTarget).tts$SetStencilBufferEnabled(true);

		stack.pushPose();

		stack.translate(0, 0, -0.5);

		mainTarget.unbindWrite();

		start();

		copyRenderTarget(mainTarget, renderTarget);

		MultiBufferSource.BufferSource botiBuffer = stencilBufferStorage.getConsumer();
		// TODO: Render Door Frame RIGHT HERE (implement datapack door frames, BOTI mask
		// named "BOTI")
		// drawStencil.accept(stack, botiBuffer);
		// botiBuffer.endBatch();

		// Enable and configure stencil buffer
		GL11.glEnable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);
		GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
		GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
		GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

		RenderSystem.depthMask(true);

		// Render mask
		stack.pushPose();

		// TODO: datapack door frame stencil here
		// Render Stencil
		GL11.glColorMask(false, false, false, false);
		// BotiPortalModel.createBodyLayer().bakeRoot().render(stack,
		// botiBuffer.getBuffer(RenderType.solid()),
		// 0xf000f0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
		drawStencil.accept(stack, botiBuffer);
		// drawFrame.accept(stack);
		botiBuffer.endBatch();
		stack.popPose();

		// Backup depth by copying to BOTI FBO
		copyDepth(renderTarget, mainTarget);
		renderTarget.bindWrite(false);
		// Clear main buffer depth
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		// Enable stencil
		GL11.glStencilMask(0x00);
		GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);

		drawFrame.accept(stack, botiBuffer);
		botiBuffer.endBatch();

		GL11.glColorMask(true, true, true, false);

		stack.pushPose();
		// stack.scale(10, 10, 10);

		// Render BOTI Scene
		RenderSystem.enableCull();
		drawScene.accept(stack, botiBuffer);
		botiBuffer.endBatch();
		RenderSystem.disableCull();
		RenderSystem.enableDepthTest();

		stack.popPose();

		GL11.glColorMask(true, true, true, true);

		// Set VBO back to main
		mainTarget.bindWrite(false);

		// Restore depth by copying from BOTI FBO back to Main
		copyColor(renderTarget, mainTarget);

		// Disable Stencil
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		GL11.glStencilMask(0xFF);

		// Set VBO to main and [insert something here] viewport
		mainTarget.bindWrite(true);

		// Copy the color from BOTI FBO to Main target
		copyColor(renderTarget, mainTarget);

		GL11.glDisable(GL11.GL_STENCIL_TEST);

		RenderSystem.depthMask(true);

		stack.popPose();
	}

	// TODO: move these back into the BOTIInit

	public void end(boolean clear) {
		renderTarget.clear(clear);
		renderTarget.unbindWrite();
	}

	public void start() {
		GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_DEBUG, GLFW.GLFW_TRUE);

		Window window = Minecraft.getInstance().getWindow();
		int width = window.getWidth();
		int height = window.getHeight();

		// Check if renderTarget needs to be reinitialized
		if (renderTarget == null || renderTarget.width != width || renderTarget.height != height) {
			renderTarget = new TextureTarget(width, height, true, Minecraft.ON_OSX);
		}

		renderTarget.bindWrite(false);
		renderTarget.checkStatus();

		if (!((IHelpWithFBOs) renderTarget).tts$IsStencilBufferEnabled()) {
			((IHelpWithFBOs) renderTarget).tts$SetStencilBufferEnabled(true);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class StencilBufferStorage extends RenderBuffers {
		private final Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> typeBufferBuilder = Util
				.make(new Object2ObjectLinkedOpenHashMap<>(), map -> put(map, getRenderType()));

		@Getter
		public final MultiBufferSource.BufferSource consumer = MultiBufferSource.immediateWithBuffers(typeBufferBuilder,
				new BufferBuilder(256));

		private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> builderStorage,
				RenderType layer) {
			builderStorage.put(layer, new BufferBuilder(layer.bufferSize()));
		}

		public static RenderType getRenderType() {
			RenderType.CompositeState parameters = RenderType.CompositeState.builder()
					.setTextureState(RenderStateShardAccessor.getBLOCK_SHEET_MIPPED())
					.setTransparencyState(RenderStateShardAccessor.getTRANSLUCENT_TRANSPARENCY())
					.setLayeringState(RenderStateShardAccessor.getNO_LAYERING()).createCompositeState(false);
			return RenderType.create("boti", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, QUADS, 256, false, true,
					parameters);
		}
	}
}
