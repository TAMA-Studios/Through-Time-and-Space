/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.subsystem;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.DematCircuitSubsystemModel;
import com.code.tama.tts.core.tileentities.multiblock.DematCircuitBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import com.code.tama.triggerapi.helpers.world.BlockUtils;

public class DematCircuitRenderer implements BlockEntityRenderer<DematCircuitBlockEntity> {

	private final DematCircuitSubsystemModel<?> model;
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID,
			"textures/tiles/subsystem/demat_circuit.png");

	public DematCircuitRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new DematCircuitSubsystemModel<>(context.bakeLayer(DematCircuitSubsystemModel.LAYER_LOCATION));
	}

	@Override
	public void render(DematCircuitBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
			MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

		if (!pBlockEntity.isFormed)
			return;

		pPoseStack.pushPose();

		pPoseStack.translate(0.5f, 1.5f, 0.5f);
		pPoseStack.mulPose(Axis.XP.rotationDegrees(180));
		pPoseStack.mulPose(Axis.YP.rotationDegrees(180));

		int light = BlockUtils.getPackedLight(pBlockEntity.getLevel(), pBlockEntity.getBlockPos());

		this.model.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), light,
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

		pPoseStack.popPose();
	}
}
