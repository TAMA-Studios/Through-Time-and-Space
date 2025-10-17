/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import static com.code.tama.tts.server.tardis.controls.AbstractControl.Spark;

import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.entities.controls.ModularControl;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.EntityHitResult;

public class ControlRenderer extends EntityRenderer<ModularControl> {
	public ControlRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull ModularControl modularControl) {
		return new ResourceLocation("");
	}

	@Override
	public void render(@NotNull ModularControl Entity, float EntityYaw, float PartialTick, @NotNull PoseStack PoseStack,
			@NotNull MultiBufferSource Buffer, int PackedLight) {
		super.render(Entity, EntityYaw, PartialTick, PoseStack, Buffer, PackedLight);
		if (Minecraft.getInstance().hitResult instanceof EntityHitResult result && result.getEntity() == Entity) {
			assert Minecraft.getInstance().player != null;
			if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof SonicItem
					|| Minecraft.getInstance().player.getOffhandItem().getItem() instanceof SonicItem)
				this.renderNameTag(Entity, Entity.TranslationKey(), PoseStack, Buffer, PackedLight);
		}

		Entity.GetControl().RenderFlightEvent(PoseStack, Buffer, Entity);

		Entity.level().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent((cap) -> {
			if (cap.GetData().isSparking())
				if (Entity.level().random.nextInt(100000) <= 5) {
					Spark(Entity.level(), Entity.position());
				}
		});
	}

	@Override
	public boolean shouldRender(@NotNull ModularControl LivingEntity, @NotNull Frustum Camera, double CamX, double CamY,
			double CamZ) {
		return super.shouldRender(LivingEntity, Camera, CamX, CamY, CamZ);
	}
}
