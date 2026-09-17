/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.console;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.consoles.TakomakModel;
import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.consoles.TakomakConsoleTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.animation.AnimationTicker;
import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class TakomakConsoleRenderer<T extends TakomakConsoleTile, C extends HierarchicalModel<Entity> & IAnimateableModel<T>>
		implements
			BlockEntityRenderer<T> {
	public static final ResourceLocation EMMISIVE = new ResourceLocation(MODID,
			"textures/tiles/console/tokamak_emmisives.png");
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/tiles/console/tokamak.png");
	public final C MODEL;

	public TakomakConsoleRenderer(BlockEntityRendererProvider.Context context, C model) {
		this.MODEL = model;
	}

	@SuppressWarnings("unchecked")
	public TakomakConsoleRenderer(BlockEntityRendererProvider.Context context) {
		this.MODEL = (C) new TakomakModel<>(context.bakeLayer(TakomakModel.LAYER_LOCATION));
	}

	@Override
	public void render(@NotNull T ConsoleTile, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		poseStack.pushPose();
		poseStack.translate(0.5, 1.467, 0.5);
		poseStack.mulPose(Axis.ZP.rotationDegrees(0xB4)); // Rot 180
		poseStack.mulPose(Axis.YP.rotationDegrees(0xB4)); // Rot 180

		assert ConsoleTile.getLevel() != null;
		if (ConsoleTile.getLevel() != null) {
			float offs;
			if (ConsoleTile.getLevel().getBlockState(ConsoleTile.getBlockPos().below())
					.getBlock() instanceof SnowLayerBlock)
				offs = 1;
			else
				offs = BlockUtils.getReverseHeightModifier(
						ConsoleTile.getLevel().getBlockState(ConsoleTile.getBlockPos().below()));
			offs -= 0.5;
			poseStack.translate(0, offs, 0);
		}

		assert Minecraft.getInstance().level != null;
		float ticks = AnimationTicker.getTicks() + partialTicks;
		this.MODEL.SetupAnimations(ConsoleTile, ticks);
		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)),
				combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(EMMISIVE)), 0xf000f0,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

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
