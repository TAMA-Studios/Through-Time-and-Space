/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.gadgets;

import com.code.tama.tts.client.models.FabricatorModel;
import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.WorkbenchTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.UVUtils;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class FabricatorRenderer<T extends WorkbenchTile, C extends HierarchicalModel<Entity> & IAnimateableModel<T>>
		implements
			BlockEntityRenderer<T> {
	public static final ResourceLocation TEXTURE = UniversalCommon.modRL("textures/tiles/fabricator.png");
	public static final ResourceLocation SPARK = UniversalCommon.modRL("textures/fabricator_electricity.png");
	public final C MODEL;
	public VertexBuffer ElectricityVBO;

	@SuppressWarnings("unchecked")
	public FabricatorRenderer(BlockEntityRendererProvider.Context context) {
		this.MODEL = (C) new FabricatorModel<>(context.bakeLayer(FabricatorModel.LAYER_LOCATION));
	}

	private void renderSpark(T Fabricator, PoseStack poseStack) {
		poseStack.pushPose();
		// poseStack.mulPose(Axis.YP.rotationDegrees(180));
		poseStack.mulPose(Axis.ZP.rotationDegrees(180));
		poseStack.translate(0, -0.1, 0.1);
		poseStack.scale(2f, 2f, 1);

		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		float y = UVUtils.PixelToCoord(16 * (Fabricator.AnimationTicks % 40), 160);
		final float sixteenth = UVUtils.PixelToCoord(16, 160);

		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, -1f, -1f, 0).uv(0, y - sixteenth).endVertex();
		buffer.vertex(matrix, 1f, -1f, 0).uv(1, y - sixteenth).endVertex();
		buffer.vertex(matrix, 1f, 1f, 0).uv(1, y).endVertex();
		buffer.vertex(matrix, -1f, 1f, 0).uv(0, y).endVertex();
		poseStack.popPose();

		BufferUploader.drawWithShader(buffer.end());
		// Tesselator.getInstance().end();

	}

	@Override
	public void render(@NotNull T Fabricator, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		poseStack.pushPose();
		Direction facing = Fabricator.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);

		poseStack.translate(0.5, 0.5, 0.5);

		poseStack.mulPose(Axis.ZP.rotationDegrees(180));

		poseStack.mulPose(Axis.YP.rotationDegrees(facing.getOpposite().toYRot()));

		poseStack.translate(0.0, -1.0, 1);
		assert Minecraft.getInstance().level != null;

		// long ticks = (TARDISLevelCapability.GetClientTARDISCapSupplier().map(tardis
		// -> tardis.getTicks()).orElse(Minecraft.getInstance().level.getGameTime()));

		long ticks = Fabricator.AnimationTicks;

		this.MODEL.SetupAnimations(Fabricator, ticks + partialTicks);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)),
				combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		int storedItems = Fabricator.getStoredItems().size();

		Item one = storedItems > 0 ? Fabricator.StoredItems.get(0) : Items.AIR;
		Item two = storedItems > 1 ? Fabricator.StoredItems.get(1) : Items.AIR;
		Item three = storedItems > 2 ? Fabricator.StoredItems.get(2) : Items.AIR;
		Item four = storedItems > 3 ? Fabricator.StoredItems.get(3) : Items.AIR;
		Item five = storedItems > 4 ? Fabricator.StoredItems.get(4) : Items.AIR;
		Item six = storedItems > 5 ? Fabricator.StoredItems.get(5) : Items.AIR;
		Item nozzle = Fabricator.nozzle == null ? Items.AIR : Fabricator.nozzle;

		poseStack.popPose();

		if (!Fabricator.Open)
			return;

		poseStack.pushPose();

		poseStack.translate(0.5, 0.5, 0.5);

		int yRot = switch (facing) {
			case NORTH -> 180;
			case EAST -> 90;
			case SOUTH -> 0;
			case WEST -> 270;
			default -> 0;
		};

		poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

		poseStack.translate(0.5, -0.5, 0.5);

		poseStack.translate(-0.5, 0.5, -0.8);
		poseStack.scale(0.25f, 0.25f, 0.25f);

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(36));
		poseStack.mulPose(Axis.XP.rotationDegrees(30));
		poseStack.translate(-1.45, 0.95, -1.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(one.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.YN.rotationDegrees(36));
		poseStack.mulPose(Axis.XP.rotationDegrees(30));
		poseStack.translate(1.45, 0.95, -1.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(two.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(36));
		poseStack.mulPose(Axis.XN.rotationDegrees(30));
		poseStack.translate(-1.45, -0.95, -1.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(three.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.YN.rotationDegrees(36));
		poseStack.mulPose(Axis.XN.rotationDegrees(30));
		poseStack.translate(1.45, -0.9, -1.5);
		Minecraft.getInstance().getItemRenderer().renderStatic(four.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.XN.rotationDegrees(67.5f));
		poseStack.mulPose(Axis.YN.rotationDegrees(15));
		poseStack.translate(-1.25, -0.65, -0.82);
		Minecraft.getInstance().getItemRenderer().renderStatic(five.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.XN.rotationDegrees(67.5f));
		poseStack.mulPose(Axis.YP.rotationDegrees(15));
		poseStack.translate(1.15, -0.65, -0.82);
		Minecraft.getInstance().getItemRenderer().renderStatic(six.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.translate(0.0, 1, -0.15);
		Minecraft.getInstance().getItemRenderer().renderStatic(nozzle.getDefaultInstance(), ItemDisplayContext.FIXED,
				combinedLight, combinedOverlay, poseStack, bufferSource, ((Level) Minecraft.getInstance().level), 1);
		poseStack.popPose();

		if (Fabricator.Fabricating) {
			RenderSystem.enableDepthTest();
			RenderSystem.disableCull();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, SPARK);

			renderSpark(Fabricator, poseStack);

			RenderSystem.disableDepthTest();
			RenderSystem.enableCull();

		}

		poseStack.popPose();
	}

	@Override
	public boolean shouldRenderOffScreen(@NotNull T p_112306_) {
		return true;
	}

	@Override
	public boolean shouldRender(T p_173568_, Vec3 p_173569_) {
		return true;
	}

	@Override
	public int getViewDistance() {
		return BlockEntityRenderer.super.getViewDistance();
	}
}
