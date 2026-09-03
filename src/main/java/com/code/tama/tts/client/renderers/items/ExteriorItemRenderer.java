/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.items;

import java.util.HashMap;
import java.util.Map;

import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.core.registries.tardis.ExteriorsRegistry;
import com.code.tama.tts.server.misc.containers.ExteriorModelContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;

public class ExteriorItemRenderer extends BlockEntityWithoutLevelRenderer {
	Map<ItemStack, RenderInfo> INFO_MAP = new HashMap<>();

	public ExteriorItemRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
		super(dispatcher, modelSet);
	}

	@Override
	public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext context,
			@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		RenderInfo info;
		if (!INFO_MAP.containsKey(stack)) {
			info = new RenderInfo();
			INFO_MAP.put(stack, info);
		} else
			info = INFO_MAP.get(stack);

		if (info.exteriorModelContainer == null) {
			info.exteriorModelContainer = ExteriorsRegistry.Get(0);

			if (stack.getTag() != null && stack.getTag().contains("BlockEntityTag")) {
				CompoundTag tag = stack.getTag().getCompound("BlockEntityTag");
				if (tag.contains("model")) {
					info.exteriorModelContainer = ExteriorModelContainer.CODEC.parse(NbtOps.INSTANCE, tag.get("model"))
							.get().orThrow();
				}
			}

			info.ext = new AbstractJSONRenderer(info.exteriorModelContainer.getModel());
			info.model = JavaJSON.getParsedJavaJSON(info.ext).getModelInfo().getModel();
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

		if (info.model != null) {
			info.model.getPart("baseRoot").render(poseStack,
					buffer.getBuffer(info.ext.getRenderType(info.exteriorModelContainer.getTexture())), packedLight,
					OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0);

			info.model.getPart("baseRoot").render(poseStack,
					buffer.getBuffer(info.ext.getRenderType(info.exteriorModelContainer.getLightMap())), 0xf000f0,
					OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 0);
		}

		poseStack.popPose();
	}

	public static class RenderInfo {
		public RenderInfo() {
			ext = null;
			exteriorModelContainer = null;
			model = null;
		}

		ExteriorModelContainer exteriorModelContainer;
		JavaJSONModel model;
		AbstractJSONRenderer ext;
	}
}
