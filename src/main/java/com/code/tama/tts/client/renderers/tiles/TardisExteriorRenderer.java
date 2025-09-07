/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONParsed;
import com.code.tama.tts.client.renderers.HalfBOTI;
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
        poseStack.translate(0.5, 1, 0.5);
        if (exteriorTile.getLevel() != null) {
            poseStack.mulPose(exteriorTile
                    .getLevel()
                    .getBlockState(exteriorTile.getBlockPos())
                    .getValue(ExteriorBlock.FACING)
                    .getOpposite()
                    .getRotation());
            poseStack.mulPose(Axis.XN.rotationDegrees(90));
        }

        try {
            HalfBOTI.render(
                    exteriorTile.getLevel(),
                    exteriorTile,
                    poseStack,
                    bufferSource,
                    partialTicks,
                    combinedLight,
                    combinedOverlay);
        } catch (Exception ignored) {
            System.out.println(ignored.getMessage());
        }

        AbstractJSONRenderer ext =
                new AbstractJSONRenderer(exteriorTile.GetVariant().GetModelName());

        JavaJSONParsed parsed = JavaJSON.getParsedJavaJSON(ext);

        parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(Math.max(FrameLeft * 13.333, 0)); // (float)
        // Math.toRadians(0);
        parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-Math.max(FrameRight * 13.333, 0));

        parsed.getModelInfo()
                .getModel()
                .renderToBuffer(
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
