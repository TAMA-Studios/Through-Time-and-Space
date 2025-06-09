/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;


import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.tts.client.models.ColinRichmondInteriorDoors;
import com.code.tama.tts.server.tileentities.DoorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ModernPoliceBoxInteriorDoorsRenderer implements BlockEntityRenderer<DoorTile> {
    public final ModelPart MODEL;
    public static ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11b.png");

    public ModernPoliceBoxInteriorDoorsRenderer(BlockEntityRendererProvider.Context context) {
        this.MODEL = context.bakeLayer(ColinRichmondInteriorDoors.LAYER_LOCATION);
    }

    @Override
    public void render(@NotNull DoorTile doorTile, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

        TEXTURE = new ResourceLocation(MODID, "textures/tiles/exterior/colin_richmond/mof_11b.png");

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        poseStack.translate(-0.5, -0.645, 0.33);
        poseStack.scale(0.43f, 0.43f, 0.43f);
        this.MODEL.yRot = (float) Math.toRadians(180.0);
        this.MODEL.render(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLight, OverlayTexture.NO_OVERLAY,
                1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }
}
