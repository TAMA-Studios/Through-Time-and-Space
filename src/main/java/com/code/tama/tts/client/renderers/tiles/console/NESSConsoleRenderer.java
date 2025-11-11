/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.console;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.server.tileentities.NESSConsoleTile;
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

import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class NESSConsoleRenderer<T extends NESSConsoleTile, C extends HierarchicalModel<Entity> & IAnimateableModel<T>>
		implements
			BlockEntityRenderer<T> {
	public static ResourceLocation CONTROLS_ONE;
	public static ResourceLocation CONTROLS_TWO;
	public static ResourceLocation EMMISIVE;
	public static ResourceLocation TEXTURE;

	public final C MODEL;

	public NESSConsoleRenderer(BlockEntityRendererProvider.Context context, C model) {
		this.MODEL = model; // context.bakeLayer(HudolinConsole.LAYER_LOCATION);
	}

	@Override
	public void render(@NotNull T ConsoleTile, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		TEXTURE = new ResourceLocation(MODID, "textures/tiles/console/ness/ness_console.png");
		EMMISIVE = new ResourceLocation(MODID, "textures/tiles/console/ness/ness_console_emmisives.png");
		CONTROLS_ONE = new ResourceLocation(MODID, "textures/tiles/console/ness/ness_controls_1.png");
		CONTROLS_TWO = new ResourceLocation(MODID, "textures/tiles/console/ness/ness_controls_2.png");

		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		poseStack.mulPose(Axis.YP.rotationDegrees(180));
		poseStack.translate(-0.5, -1.45, 0.5);
		assert ConsoleTile.getLevel() != null;
		if (ConsoleTile.getLevel() != null) {
			float offs;
			if (ConsoleTile.getLevel().getBlockState(ConsoleTile.getBlockPos().below())
					.getBlock() instanceof SnowLayerBlock)
				offs = 1;
			else
				offs = BlockUtils.getReverseHeightModifier(
						ConsoleTile.getLevel().getBlockState(ConsoleTile.getBlockPos().below()));
			poseStack.translate(0, offs, 0);
		}
		poseStack.scale(1f, 1f, 1f);
		assert Minecraft.getInstance().level != null;
		float ticks = Minecraft.getInstance().level.getGameTime() + partialTicks;
		this.MODEL.SetupAnimations(ConsoleTile, ticks);
		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)),
				combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(EMMISIVE)), 0xf000f0,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(CONTROLS_ONE)),
				Light(combinedLight, true), OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(CONTROLS_TWO)),
				Light(combinedLight, false), OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		poseStack.popPose();
	}

	public int Light(int combinedLight, boolean inverse) {
		if (inverse)
			return Minecraft.getInstance().level.getGameTime() % 80 <= 40 ? 0xf000f0 : combinedLight;
		else
			return Minecraft.getInstance().level.getGameTime() % 80 >= 40 ? 0xf000f0 : combinedLight;
	}
}
