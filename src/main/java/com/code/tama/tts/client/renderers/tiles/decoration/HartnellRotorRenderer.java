/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.decoration;

import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.core.IAnimateableModel;
import com.code.tama.tts.server.tileentities.HartnellRotorTile;
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

public class HartnellRotorRenderer<T extends HartnellRotorTile, C extends HierarchicalModel<Entity> & IAnimateableModel<T>>
		implements
			BlockEntityRenderer<T> {
	public static ResourceLocation EMMISIVE_TEXTURE;
	public static ResourceLocation TEXTURE;
	public final C MODEL;

	public HartnellRotorRenderer(BlockEntityRendererProvider.Context context, C model) {
		this.MODEL = model; // context.bakeLayer(HudolinConsole.LAYER_LOCATION);
	}

	@Override
	public void render(@NotNull T Tile, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		TEXTURE = new ResourceLocation(MODID, "textures/tiles/hartnell_rotor.png");
		EMMISIVE_TEXTURE = new ResourceLocation(MODID, "textures/tiles/hartnell_rotor_emmisive.png");

		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		poseStack.translate(0.5, -1.5, -0.5);
		poseStack.scale(1f, 1f, 1f);
		assert Minecraft.getInstance().level != null;
		float ticks = Minecraft.getInstance().level.getGameTime() + partialTicks;
		this.MODEL.SetupAnimations(Tile, ticks);
		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)),
				combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutout(EMMISIVE_TEXTURE)),
				0xf000f0, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
		poseStack.popPose();
	}
}
