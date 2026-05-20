/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.decoration;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.VortexCannon;
import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.core.tileentities.VortexCannonTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VortexCannonRenderer<T extends VortexCannonTile, C extends HierarchicalModel<Entity> & IAnimateableModel<T>>
		implements
			BlockEntityRenderer<T> {

	private final C MODEL;
	public static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/tiles/vortex_cannon.png");

	public VortexCannonRenderer(BlockEntityRendererProvider.Context context) {
		this.MODEL = (C) new VortexCannon<VortexCannonTile>(context.bakeLayer(VortexCannon.LAYER_LOCATION));
	}

	@Override
	public void render(T pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource,
			int pPackedLight, int pPackedOverlay) {

		pPoseStack.pushPose();

		pPoseStack.translate(0.5f, 1.5f, 0.5f);
		pPoseStack.mulPose(Axis.XP.rotationDegrees(180));

		// int light = BlockUtils.getPackedLight(pBlockEntity.getLevel(),
		// pBlockEntity.getBlockPos().above());

		this.MODEL.SetupAnimations(pBlockEntity, pPartialTick);
		this.MODEL.renderToBuffer(pPoseStack, pBufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), pPackedLight,
				OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

		pPoseStack.popPose();
	}
}
