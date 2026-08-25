/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * Renders an animated item entirely client-side, no BlockEntity involved.
 * <br />
 * IMPORTANT: this instance is meant to be a SINGLETON per item type, build it
 * once (e.g. a field on your IClientItemExtensions, or cached via GeoHelper,
 * see GeoHelper.getRenderer) and always return that same instance from
 * getCustomRenderer(). Calling `new GeoItemRenderer(...)` every time you need
 * one both re-does nothing useful (the model/texture are immutable and safe to
 * share) and, worse, means any animation state you set on one instance is
 * invisible to whichever instance actually gets rendered. <br />
 * Per-stack animation state deliberately does NOT live on this class, it's
 * stateless. Which animation is playing and when it started are read fresh from
 * the ItemStack's own NBT every render call (see GeoHelper.playAnimation to set
 * it). That's what makes two stacks of the same item animate independently
 * without you having to manage per-stack objects yourself.
 */
public class GeoItemRenderer extends BlockEntityWithoutLevelRenderer {

	public static final String NBT_ANIM_NAME = "GeoAnimName";
	public static final String NBT_ANIM_START = "GeoAnimStart";

	private final GeoModel model;
	private final ResourceLocation texture;
	private final Map<String, GeoAnimation> animations;

	public GeoItemRenderer(GeoModel model, ResourceLocation texture, Map<String, GeoAnimation> animations) {
		super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		this.model = model;
		this.texture = texture;
		this.animations = animations;
	}

	@Override
	public void renderByItem(ItemStack stack, @NotNull ItemDisplayContext displayContext, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		var mc = Minecraft.getInstance();
		float nowTicks = mc.level != null ? mc.level.getGameTime() : 0;
		float partialTick = mc.getDeltaFrameTime();

		model.resetPose();
		CompoundTag tag = stack.getTag();
		if (tag != null && tag.contains(NBT_ANIM_NAME)) {
			GeoAnimation anim = animations.get(tag.getString(NBT_ANIM_NAME));
			if (anim != null) {
				long startTick = tag.getLong(NBT_ANIM_START);
				float elapsedSeconds = ((nowTicks + partialTick) - startTick) / 20.0f;
				anim.apply(model, elapsedSeconds);
			}
		}

		poseStack.pushPose();
		// Blockbench pivot space -> item origin adjustment; tweak if your model sits
		// off-center.
		poseStack.translate(0.5, 0.5, 0.5);

		var consumer = buffer.getBuffer(RenderType.entityCutout(texture));
		GeoRenderer.render(model, poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

		poseStack.popPose();
	}
}