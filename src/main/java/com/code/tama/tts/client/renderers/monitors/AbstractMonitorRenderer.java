/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.monitors;

import com.code.tama.triggerapi.helpers.rendering.StencilUtils;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.client.UI.category.UICategory;
import com.code.tama.tts.client.UI.component.all.UIComponentPower;
import com.code.tama.tts.client.UI.component.core.UIComponent;
import com.code.tama.tts.server.registries.misc.UICategoryRegistry;
import com.code.tama.tts.server.registries.misc.UIComponentRegistry;
import com.code.tama.tts.server.tileentities.monitors.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
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
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static com.code.tama.tts.client.UI.category.UICategory.RenderText;
import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class AbstractMonitorRenderer<T extends AbstractMonitorTile> implements BlockEntityRenderer<T> {
	public static final ResourceLocation GALLIFREYAN = new ResourceLocation(TTSMod.MODID, "textures/tiles/monitor/galifrayan.png");
	public final BlockEntityRendererProvider.Context context;
	public static final int fullBright = 0xF000F0;
	int light = 0xf00f0;

	UICategory category;

	public AbstractMonitorRenderer(BlockEntityRendererProvider.Context context) {
		this.context = context;
	}

	@Override
	public void render(@NotNull T monitor, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (monitor.getLevel() == null)
			return;
		StencilUtils.DrawStencil(poseStack, (pose) -> {
			pose.pushPose();
			this.ApplyDefaultTransforms(pose, monitor);
			// poseStack.translate(0.5, 0.5, 0);

			pose.translate(-44, -0.5, 0);
			pose.scale(5.5f, 5.5f, 0);

			renderFrame(monitor, pose, bufferSource, fullBright);
			pose.popPose();
		}, (pose) -> {

			// GetTARDISCapSupplier(monitor.getLevel()).ifPresent(cap -> {

			boolean isInTARDIS = GetTARDISCapSupplier(monitor.getLevel()).isPresent();

			pose.pushPose();

			this.ApplyDefaultTransforms(pose, monitor);

			if (this.category.getID() != monitor.categoryID) {
				UICategoryRegistry.UI_CATEGORIES.getEntries().forEach(reg -> {
					if (reg.get().getID() == monitor.getCategoryID()) {
						this.category = reg.get();
					}
				});
			}

			if (monitor.isPowered()) {
				if (isInTARDIS)
					this.category.Render(monitor, pose, bufferSource, combinedLight);
				else
					RenderText(monitor, "Not in a TARDIS!", pose, bufferSource, -40, 25);
			}

			pose.popPose();
			pose.pushPose();

			this.ApplyDefaultTransforms(pose, monitor);

			renderUIComponents(monitor, pose, bufferSource, light);

			pose.popPose();
			pose.pushPose();

			this.ApplyDefaultTransforms(pose, monitor);

			renderRotatingImage(monitor, pose, bufferSource, light);

			pose.popPose();

			pose.pushPose();

			this.ApplyDefaultTransforms(pose, monitor);

			renderBackground(monitor, pose, bufferSource, light);

			pose.popPose();

			RenderSystem.enableDepthTest();
		});
	}

	private void renderRotatingImage(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		if (!monitor.isPowered())
			return;

		ResourceLocation texture = new ResourceLocation(TTSMod.MODID, "textures/tiles/monitor/galifrayan.png");
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderTexture(0, texture);

        assert monitor.getLevel() != null;
        float rotationAngle = (monitor.getLevel().getGameTime() % 360) + Minecraft.getInstance().getFrameTime();

		poseStack.translate(25, 70, 0);
		poseStack.scale(20, 20, 20);
		poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 0).endVertex();
		buffer.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 0).endVertex();
		buffer.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 1).endVertex();
		buffer.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 1).endVertex();

		BufferUploader.drawWithShader(buffer.end());
		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();

		poseStack.mulPose(Axis.YP.rotationDegrees(180));
	}

	private void renderUIComponents(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		ResourceLocation texture = new ResourceLocation(TTSMod.MODID, "textures/gui/button.png");
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();

		poseStack.translate(-45.41, -1.7, 0);
		poseStack.scale(5.67f, 5.67f, 0);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		for (RegistryObject<UIComponent> object : UIComponentRegistry.UI_COMPONENTS.getEntries()) {
			UIComponent component = object.get();
			RenderSystem.setShaderTexture(0, component.GetIcon());
			if (component.category.getID() != monitor.categoryID && component.category != UICategoryRegistry.ALL.get())
				continue;

			if ((component instanceof UIComponentPower) || monitor.isPowered()) {
				buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

				float XStart = component.XYBounds().get(Axis.XP)[0];
				float YStart = component.XYBounds().get(Axis.YP)[0];
				float XEnd = component.XYBounds().get(Axis.XP)[1];
				float YEnd = component.XYBounds().get(Axis.YP)[1];

				buffer.vertex(matrix, XStart, YEnd, 0).uv(0, 1).endVertex();
				buffer.vertex(matrix, XEnd, YEnd, 0).uv(1, 1).endVertex();
				buffer.vertex(matrix, XEnd, YStart, 0).uv(1, 0).endVertex();
				buffer.vertex(matrix, XStart, YStart, 0).uv(0, 0).endVertex();
				BufferUploader.drawWithShader(buffer.end());
			}

			// buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			//
			// float XStart = 0;
			// float YStart = 0;
			// float XEnd = 16;
			// float YEnd = 16;
			//
			// buffer.vertex(matrix, XStart, YEnd, 0).uv(0, 1).endVertex();
			// buffer.vertex(matrix, XEnd, YEnd, 0).uv(1, 1).endVertex();
			// buffer.vertex(matrix, XEnd, YStart, 0).uv(1, 0).endVertex();
			// buffer.vertex(matrix, XStart, YStart, 0).uv(0, 0).endVertex();
			// BufferUploader.drawWithShader(buffer.end());
		}

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();

		poseStack.mulPose(Axis.YP.rotationDegrees(180));
	}

	private void renderBackground(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		if (!monitor.isPowered())
			return;
		ResourceLocation texture;
		if (this.category != null)
			texture = this.category.getOverlay();
		else
			texture = new ResourceLocation(TTSMod.MODID, "textures/gui/overlay.png");
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();

		poseStack.translate(-44, -0.5, 0);
		poseStack.scale(5.5f, 5.5f, 0);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		RenderSystem.setShaderTexture(0, texture);

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		float XStart = 0;
		float YStart = 0;
		float XEnd = 16;
		float YEnd = 16;

		buffer.vertex(matrix, XStart, YEnd, 0).uv(0, 1).endVertex();
		buffer.vertex(matrix, XEnd, YEnd, 0).uv(1, 1).endVertex();
		buffer.vertex(matrix, XEnd, YStart, 0).uv(1, 0).endVertex();
		buffer.vertex(matrix, XStart, YStart, 0).uv(0, 0).endVertex();
		BufferUploader.drawWithShader(buffer.end());

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();

		poseStack.mulPose(Axis.YP.rotationDegrees(180));
	}

	private void renderFrame(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource,
			int combinedLight) {
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.disableBlend();
		RenderSystem.enableDepthTest();

		poseStack.translate(-0.25, -0.25, 0);
		poseStack.scale(1.0325f, 1.0325f, 0);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

		float XStart = 0;
		float YStart = 0;
		float XEnd = 16;
		float YEnd = 16;

		buffer.vertex(matrix, XStart, YEnd, 0).endVertex();
		buffer.vertex(matrix, XEnd, YEnd, 0).endVertex();
		buffer.vertex(matrix, XEnd, YStart, 0).endVertex();
		buffer.vertex(matrix, XStart, YStart, 0).endVertex();

		RenderSystem.setShaderColor(1, 1, 1, 1);
		BufferUploader.drawWithShader(buffer.end());

		RenderSystem.disableDepthTest();
		RenderSystem.enableBlend();

		poseStack.mulPose(Axis.YP.rotationDegrees(180));
	}

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
