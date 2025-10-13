/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.server.tileentities.SonicConfiguratorTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class SonicConfiguratorRenderer implements BlockEntityRenderer<SonicConfiguratorTileEntity> {
  public static final int fullBright = 0xf000f0;
  public final BlockEntityRendererProvider.Context context;

  public SonicConfiguratorRenderer(BlockEntityRendererProvider.Context context) {
    this.context = context;
  }

  @Override
  public void render(
      @NotNull SonicConfiguratorTileEntity sonicConfiguratorTileEntity,
      float partialTicks,
      @NotNull PoseStack poseStack,
      @NotNull MultiBufferSource bufferSource,
      int combinedLight,
      int combinedOverlay) {
    if (sonicConfiguratorTileEntity.getLevel() == null) return;
    poseStack.pushPose();
    poseStack.translate(0.5, 0.4, 0.5);
    poseStack.mulPose(Axis.YP.rotationDegrees(90));
    poseStack.mulPose(Axis.XP.rotationDegrees(90));
    Minecraft.getInstance()
        .getItemRenderer()
        .renderStatic(
            sonicConfiguratorTileEntity.getStack(),
            ItemDisplayContext.NONE,
            combinedLight,
            OverlayTexture.NO_OVERLAY,
            poseStack,
            bufferSource,
            sonicConfiguratorTileEntity.getLevel(),
            0);
    poseStack.popPose();
  }
}
