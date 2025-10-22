/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.items;

import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.code.tama.tts.server.registries.tardis.ExteriorsRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;

public class ExteriorItemRenderer extends BlockEntityWithoutLevelRenderer {
	ExteriorModelContainer exteriorModelContainer;
	JavaJSONModel model;
	public ExteriorItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
		super(dispatcher, modelSet);
	}

	@Override
	public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context,
			@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		// if(dummyState == null)
		// dummyState = TTSBlocks.EXTERIOR_BLOCK.get().defaultBlockState();
		// if(dummyBlockEntity == null) {
		// dummyBlockEntity = new ExteriorTile(BlockPos.ZERO, dummyState);
		// dummyBlockEntity.ShouldMakeDimOnNextTick = false;
		// dummyBlockEntity.setTransparency(1);
		// dummyBlockEntity.Model = ExteriorsRegistry.Get(0);
		// }
		//
		// if(renderer == null) renderer = (TardisExteriorRenderer<ExteriorTile>)
		// Minecraft.getInstance()
		// .getBlockEntityRenderDispatcher().getRenderer(dummyBlockEntity);
		if (exteriorModelContainer == null) {
			exteriorModelContainer = ExteriorsRegistry.Get(0);
			model = JavaJSON.getParsedJavaJSON(new AbstractJSONRenderer(exteriorModelContainer.getModel()))
					.getModelInfo().getModel();
		}
		poseStack.pushPose();

		poseStack.scale(0.35f, 0.35f, 0.35f);
		poseStack.translate(1.5, -0.25f, 0);

		if (context.equals(ItemDisplayContext.GUI)) {
			poseStack.mulPose(Axis.XP.rotationDegrees(20f));
			if (Minecraft.getInstance().level == null)
				poseStack.mulPose(Axis.YP.rotationDegrees(220f));
			else
				poseStack.mulPose(Axis.YP.rotationDegrees((float) Minecraft.getInstance().level.getGameTime() % 360));

			poseStack.translate(-0.5, 0, -0.5);

		} else {
			poseStack.scale(0.3f, 0.3f, 0.3f);
		}

		if (model != null) {
			model.renderToBuffer(poseStack, buffer.getBuffer(model.renderType(exteriorModelContainer.getTexture())),
					0xf000f0, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
		}

		poseStack.popPose();
	}
}
