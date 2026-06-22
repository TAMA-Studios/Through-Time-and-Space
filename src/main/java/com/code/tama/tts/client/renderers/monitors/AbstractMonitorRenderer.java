/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import static com.code.tama.tts.client.UI.category.UICategory.RenderText;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.UI.category.UICategory;
import com.code.tama.tts.client.UI.component.all.UIComponentPower;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.core.registries.misc.UICategoryRegistry;
import com.code.tama.tts.core.registries.misc.UIComponentRegistry;
import com.code.tama.tts.core.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.RegistryObject;

public class AbstractMonitorRenderer<T extends AbstractMonitorTile> implements BlockEntityRenderer<T> {
	public static final ResourceLocation GALLIFREYAN = new ResourceLocation(TTSMod.MODID,
			"textures/tiles/monitor/galifrayan.png");
	public final BlockEntityRendererProvider.Context context;
	public static final int fullBright = 0xF000F0;

	UICategory category;

	public AbstractMonitorRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(@NotNull T monitor, float partialTicks, @NotNull PoseStack pose,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (monitor.getLevel() == null)
			return;

		boolean isInTARDIS = GetTARDISCapSupplier(monitor.getLevel()).isPresent();

		// Resolve category once per frame if needed
		if (this.category == null || this.category.getID() != monitor.categoryID) {
			UICategoryRegistry.UI_CATEGORIES.getEntries().forEach(reg -> {
				if (reg.get().getID() == monitor.getCategoryID()) {
					this.category = reg.get();
				}
			});
		}

		// 1. Background (bottom layer)
		pose.pushPose();
		this.ApplyDefaultTransforms(pose, monitor);
		renderBackground(monitor, pose, bufferSource, fullBright);
		pose.popPose();

		// 2. Category content / "not in TARDIS" text
		pose.pushPose();
		this.ApplyDefaultTransforms(pose, monitor);
		if (monitor.isPowered()) {
			if (isInTARDIS)
				this.category.Render(monitor, pose, bufferSource, fullBright);
			else
				RenderText(monitor, "Not in a TARDIS!", pose, bufferSource, -40, 25);
		}
		pose.popPose();

		// 3. UI Components
		pose.pushPose();
		this.ApplyDefaultTransforms(pose, monitor);
		renderUIComponents(monitor, pose, bufferSource, fullBright);
		pose.popPose();

		// 4. Rotating Gallifreyan image (top layer)
		pose.pushPose();
		this.ApplyDefaultTransforms(pose, monitor);
		renderRotatingImage(monitor, pose, bufferSource, fullBright);
		pose.popPose();
	}

	// ---------------------------------------------------------------------------
	// Rotating Gallifreyan image
	// ---------------------------------------------------------------------------

	private void renderRotatingImage(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		if (!monitor.isPowered())
			return;

		// Flush any pending MultiBufferSource geometry before raw GL work
		if (bufferSource instanceof MultiBufferSource.BufferSource bs)
			bs.endBatch();

		assert monitor.getLevel() != null;
		float rotationAngle = (monitor.getLevel().getGameTime() % 360) + Minecraft.getInstance().getFrameTime();

		poseStack.pushPose();
		poseStack.translate(25, 70, 0);
		poseStack.scale(20, 20, 20);
		poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		// Set state immediately before draw — strict ordering
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GALLIFREYAN);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 0).endVertex();
		buffer.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 0).endVertex();
		buffer.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 1).endVertex();
		buffer.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 1).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		// Reset state
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();

		poseStack.popPose();
	}

	// ---------------------------------------------------------------------------
	// UI Components
	// ---------------------------------------------------------------------------

	private void renderUIComponents(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		// Flush pending geometry before raw GL work
		if (bufferSource instanceof MultiBufferSource.BufferSource bs)
			bs.endBatch();

		poseStack.pushPose();
		poseStack.translate(-45.41, -1.7, 0);
		poseStack.scale(5.67f, 5.67f, 0.001f);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);

		for (RegistryObject<UIComponent> object : UIComponentRegistry.UI_COMPONENTS.getEntries()) {
			UIComponent component = object.get();

			if (component.category.getID() != monitor.categoryID && component.category != UICategoryRegistry.ALL.get())
				continue;

			if ((component instanceof UIComponentPower) || monitor.isPowered()) {
				// Strict ordering: shader → texture → color → draw
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderTexture(0, component.GetIcon());
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

				float XStart = component.XYBounds().get(Axis.XP)[0];
				float YStart = component.XYBounds().get(Axis.YP)[0];
				float XEnd = component.XYBounds().get(Axis.XP)[1];
				float YEnd = component.XYBounds().get(Axis.YP)[1];

				buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
				buffer.vertex(matrix, XStart, YEnd, 0).uv(0, 1).endVertex();
				buffer.vertex(matrix, XEnd, YEnd, 0).uv(1, 1).endVertex();
				buffer.vertex(matrix, XEnd, YStart, 0).uv(1, 0).endVertex();
				buffer.vertex(matrix, XStart, YStart, 0).uv(0, 0).endVertex();
				BufferUploader.drawWithShader(buffer.end());

				// Reset color after every individual draw
				RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
			}
		}

		// Restore state
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		poseStack.popPose();
	}

	// ---------------------------------------------------------------------------
	// Background overlay
	// ---------------------------------------------------------------------------

	private void renderBackground(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		if (!monitor.isPowered())
			return;

		// Flush pending geometry before raw GL work
		if (bufferSource instanceof MultiBufferSource.BufferSource bs)
			bs.endBatch();

		ResourceLocation texture = (this.category != null)
				? this.category.getOverlay()
				: new ResourceLocation(TTSMod.MODID, "textures/gui/overlay.png");

		poseStack.pushPose();
		poseStack.translate(-44, -0.5, 0);
		poseStack.scale(5.5f, 5.5f, 0.001f);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		// Strict ordering: shader → texture → color → draw
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.depthMask(false);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, 0f, 16f, 0).uv(0, 1).endVertex();
		buffer.vertex(matrix, 16f, 16f, 0).uv(1, 1).endVertex();
		buffer.vertex(matrix, 16f, 0f, 0).uv(1, 0).endVertex();
		buffer.vertex(matrix, 0f, 0f, 0).uv(0, 0).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		// Reset state
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.depthMask(true);
		RenderSystem.disableBlend();

		poseStack.popPose();
	}

	// ---------------------------------------------------------------------------
	// Frame (currently unused in main render, kept for reference)
	// ---------------------------------------------------------------------------

	private void renderFrame(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		if (bufferSource instanceof MultiBufferSource.BufferSource bs)
			bs.endBatch();

		poseStack.pushPose();
		poseStack.translate(-0.25, -0.25, 0);
		poseStack.scale(1.0325f, 1.0325f, 0.001f);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		buffer.vertex(matrix, 0f, 16f, 0).endVertex();
		buffer.vertex(matrix, 16f, 16f, 0).endVertex();
		buffer.vertex(matrix, 16f, 0f, 0).endVertex();
		buffer.vertex(matrix, 0f, 0f, 0).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

		poseStack.popPose();
	}

	// ---------------------------------------------------------------------------
	// Transform helpers
	// ---------------------------------------------------------------------------

	public float Offset() {
		return 44.3f;
	}

	public void ApplyCustomTransforms(PoseStack stack) {
	}

	public void ApplyDefaultTransforms(PoseStack poseStack, AbstractMonitorTile monitor) {
		poseStack.translate(0.5, 0.98, 0.5);
		poseStack.scale(-0.011f, -0.011f, 0.011f);

		BlockState state = monitor.getBlockState();
		Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

		switch (facing) {
			case NORTH -> poseStack.translate(0, 0, -Offset());
			case SOUTH -> poseStack.translate(0, 0, Offset());
			case WEST -> poseStack.translate(Offset(), 0, 0);
			case EAST -> poseStack.translate(-Offset(), 0, 0);
		}

		float yaw = switch (facing) {
			case NORTH -> 0;
			case SOUTH -> 180;
			case WEST -> -90;
			case EAST -> 90;
			default -> 0;
		};

		poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
		this.ApplyCustomTransforms(poseStack);
	}
}