/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.tts.client.renderers.HalfBOTIRenderer;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.mixin.client.ModelPartAccessor;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> implements BlockEntityRenderer<T> {
    int DoorsOpen = 0;
    double FrameLeft = 0;
    double FrameRight = 0;
    float OldFrameTime = 0;

    public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            @NotNull T exteriorTile,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int combinedLight,
            int combinedOverlay) {

        this.DoorsOpen = exteriorTile.DoorsOpen();

        float transparency = exteriorTile.getTransparency();

        if (this.OldFrameTime != partialTicks) {

            if (this.DoorsOpen > 0) {
                if (this.FrameRight < 5.625) this.FrameRight++;
                this.OldFrameTime = partialTicks;
            } else {
                if (this.FrameRight > 0) this.FrameRight--;
            }

            if (this.DoorsOpen == 2) {
                if (this.FrameLeft < 5.625) this.FrameLeft++;
                this.OldFrameTime = partialTicks;
            } else {
                if (this.FrameLeft > 0) this.FrameLeft--;
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

        ModelPart parsedPart = (parsed.getPart("RightDoor").modelPart);

        ((ModelPartAccessor) parsedPart).getChildren();

        ModelPart rightDoor = new ModelPart(
                ((ModelPartAccessor) parsedPart).getCubes(),
                ((ModelPartAccessor) parsed.getPart("RightDoor").modelPart).getChildren());
        try {

            //            parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(Math.max(FrameLeft * 13.333, 0)); //
            // (float)
            // Math.toRadians(0);
            //            parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-Math.max(FrameRight * 13.333, 0));

            parsed.getPart("base")
                    .render(
                            poseStack,
                            bufferSource.getBuffer(ext.getRenderType()),
                            combinedLight,
                            OverlayTexture.NO_OVERLAY,
                            1.0f,
                            1.0f,
                            1.0f,
                            transparency);

            poseStack.pushPose();

            poseStack.mulPose(Axis.YP.rotationDegrees((float) -Math.max(FrameRight * 13.333, 0)));
            rightDoor.render(
                    poseStack,
                    bufferSource.getBuffer(ext.getRenderType()),
                    combinedLight,
                    OverlayTexture.NO_OVERLAY,
                    1.0f,
                    1.0f,
                    1.0f,
                    transparency);

            poseStack.popPose();
            //            parsed.renderToBuffer(
            //                    poseStack,
            //                    bufferSource.getBuffer(ext.getRenderType()),
            //                    combinedLight,
            //                    OverlayTexture.NO_OVERLAY,
            //                    1.0f,
            //                    1.0f,
            //                    1.0f,
            //                    transparency);
        } catch (Exception e) {
            e.printStackTrace();
        }
        poseStack.popPose();
    }
}
