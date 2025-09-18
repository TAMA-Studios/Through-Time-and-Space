/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.client.renderers.HalfBOTIRenderer;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> implements BlockEntityRenderer<T> {

    public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            @NotNull T exteriorTile,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int combinedLight,
            int combinedOverlay) {

        float transparency = exteriorTile.getTransparency();
        ExteriorAnimationData data = exteriorTile.exteriorAnimationData;

        if (data.FrameTimeO != partialTicks) {

            if (exteriorTile.DoorsOpen() > 0) {
                if (data.FrameRight < 5.625) data.FrameRight++;
                data.FrameTimeO = partialTicks;
            } else {
                if (data.FrameRight > 0) data.FrameRight--;
            }

            if (exteriorTile.DoorsOpen() == 2) {
                if (data.FrameLeft < 5.625) data.FrameLeft++;
                data.FrameTimeO = partialTicks;
            } else {
                if (data.FrameLeft > 0) data.FrameLeft--;
            }
        }

        poseStack.pushPose();
        float offs;
        if (exteriorTile.getLevel() != null)
            offs = -BlockUtils.getReverseHeightModifier(exteriorTile
                    .getLevel()
                    .getBlockState(exteriorTile.getBlockPos().below()));
        else offs = 0;
        poseStack.translate(0.5, offs + 1.5, 0.5);

        if (exteriorTile.getLevel() != null) {
            poseStack.mulPose(exteriorTile
                    .getLevel()
                    .getBlockState(exteriorTile.getBlockPos())
                    .getValue(ExteriorBlock.FACING)
                    .getOpposite()
                    .getRotation());
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
            poseStack.mulPose(Axis.ZN.rotationDegrees(180));
        }

        //                TardisBotiRenderer.render(
        //                        exteriorTile.getLevel(),
        //                        exteriorTile,
        //                        poseStack,
        //                        bufferSource,
        //                        partialTicks,
        //                        combinedLight,
        //                        combinedOverlay);

        HalfBOTIRenderer.render(
                exteriorTile.getLevel(),
                exteriorTile,
                poseStack,
                bufferSource,
                partialTicks,
                combinedLight,
                combinedOverlay);

        AbstractJSONRenderer ext = new AbstractJSONRenderer(exteriorTile.getModelIndex());

        JavaJSONModel parsed = JavaJSON.getParsedJavaJSON(ext).getModelInfo().getModel();

        parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(Math.max(data.FrameLeft * 13.333, 0)); // (float)
        // Math.toRadians(0);
        parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-Math.max(data.FrameRight * 13.333, 0));

        parsed.renderToBuffer(
                poseStack,
                bufferSource.getBuffer(ext.getRenderType()),
                combinedLight,
                OverlayTexture.NO_OVERLAY,
                1.0f,
                1.0f,
                1.0f,
                transparency);

        poseStack.popPose();
    }
}
