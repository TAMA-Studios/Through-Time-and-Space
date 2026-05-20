/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.decoration;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.CoralConsoleTopper;
import com.code.tama.tts.core.tileentities.CoralConsoleTopperTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

@OnlyIn(Dist.CLIENT)
public class CoralConsoleTopperRenderer implements BlockEntityRenderer<CoralConsoleTopperTile> {

	private final CoralConsoleTopper<?> model;
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID,
			"textures/tiles/console/coral/console.png");

	public CoralConsoleTopperRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new CoralConsoleTopper<>(context.bakeLayer(CoralConsoleTopper.LAYER_LOCATION));

	}

	@Override
	public void render(CoralConsoleTopperTile pBlockEntity, float pPartialTick, PoseStack pPoseStack,
			MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

		pPoseStack.pushPose();

		pPoseStack.translate(0.5f, -2.5f, 0.5f);
		pPoseStack.mulPose(Axis.XP.rotationDegrees(180));

		int light = BlockUtils.getPackedLight(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above());

		this.model.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), light,
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

		pPoseStack.popPose();
	}
}
