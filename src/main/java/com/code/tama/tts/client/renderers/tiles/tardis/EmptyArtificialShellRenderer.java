/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.tts.client.models.ShellBaseModel;
import com.code.tama.tts.server.tileentities.EmptyArtificialShellTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;

import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class EmptyArtificialShellRenderer<T extends EmptyArtificialShellTile> implements BlockEntityRenderer<T> {
	public final ShellBaseModel<T> model;
	public static final ResourceLocation TEXTURE = UniversalCommon.modRL("textures/tiles/empty_shell.png");

	public EmptyArtificialShellRenderer(BlockEntityRendererProvider.Context context) {
		this.model = new ShellBaseModel<T>(context.bakeLayer(ShellBaseModel.LAYER_LOCATION));
	}

	@Override
	public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack stack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (exteriorTile.getLevel() != null
				&& exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos()).getBlock().equals(Blocks.AIR))
			return;

		stack.pushPose();

		stack.mulPose(Axis.ZP.rotationDegrees(180));

		float offs;
		if (exteriorTile.getLevel() != null)
			offs = -BlockUtils.getReverseHeightModifier(
					exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos().below()));
		else
			offs = 0;
		stack.translate(-0.5, -(offs + 1.5), 0.5);

		this.model.SetupAnimations(exteriorTile, partialTicks);

		this.model.renderToBuffer(stack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), combinedLight,
				OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);

		stack.popPose();
	}
}