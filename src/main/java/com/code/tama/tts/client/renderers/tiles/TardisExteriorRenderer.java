/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.client.renderers.HalfBOTIRenderer;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> implements BlockEntityRenderer<T> {

    public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            @NotNull T exteriorTile,
            float partialTicks,
            @NotNull PoseStack stack,
            @NotNull MultiBufferSource bufferSource,
            int combinedLight,
            int combinedOverlay) {
        if (exteriorTile.getLevel() != null
                && exteriorTile
                        .getLevel()
                        .getBlockState(exteriorTile.getBlockPos())
                        .getBlock()
                        .equals(Blocks.AIR)) return;

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

        stack.pushPose();
        float offs;
        if (exteriorTile.getLevel() != null)
            offs = -BlockUtils.getReverseHeightModifier(exteriorTile
                    .getLevel()
                    .getBlockState(exteriorTile.getBlockPos().below()));
        else offs = 0;
        stack.translate(0.5, offs + 1.5, 0.5);

        if (exteriorTile.getLevel() != null) {
            stack.mulPose(exteriorTile
                    .getLevel()
                    .getBlockState(exteriorTile.getBlockPos())
                    .getValue(ExteriorBlock.FACING)
                    .getOpposite()
                    .getRotation());
            stack.mulPose(Axis.XN.rotationDegrees(90));
            stack.mulPose(Axis.ZN.rotationDegrees(180));
        }

        //                TardisBotiRenderer.render(
        //                        exteriorTile.getLevel(),
        //                        exteriorTile,
        //                        poseStack,
        //                        bufferSource,
        //                        partialTicks,
        //                        combinedLight,
        //                        combinedOverlay);


        AbstractJSONRenderer ext = new AbstractJSONRenderer(exteriorTile.getModelIndex());

        JavaJSONModel parsed = JavaJSON.getParsedJavaJSON(ext).getModelInfo().getModel();

        ModelPart lDoor = parsed.getPart("LeftDoor").modelPart;
        ModelPart rDoor = parsed.getPart("RightDoor").modelPart;
        ModelPart base = parsed.getPart("Base").modelPart;
        ModelPart boti = parsed.getPart("BOTI").modelPart;
        ModelPart partialBOTI = parsed.getPart("PartialBOTI").modelPart;
        lDoor.yRot = (float) Math.toRadians(Math.max(data.FrameLeft * 13.333, 0));
        rDoor.yRot = (float) Math.toRadians(-Math.max(data.FrameRight * 13.333, 0));

        base.render(
                stack,
                bufferSource.getBuffer(ext.getRenderType()),
                combinedLight,
                OverlayTexture.NO_OVERLAY,
                1.0f,
                1.0f,
                1.0f,
                transparency);

        if(false) // TODO: CONFIG FOR END PORTAL/GREEN SCREEN BOTI
            HalfBOTIRenderer.render(
                    exteriorTile.getLevel(),
                    exteriorTile,
                    stack,
                    bufferSource,
                    partialTicks,
                    combinedLight,
                    combinedOverlay);
        else {
            stack.pushPose();
            exteriorTile.getFBOContainer().Render(stack, (pose) -> {
                boti.render(
                        pose,
                        bufferSource.getBuffer(ext.getRenderType()),
                        combinedLight,
                        OverlayTexture.NO_OVERLAY,
                        1.0f,
                        1.0f,
                        1.0f,
                        transparency);
                if(true) // TODO: CONFIG FOR PARTIAL BOTI!
                    partialBOTI.render(
                        pose,
                        bufferSource.getBuffer(ext.getRenderType()),
                        combinedLight,
                        OverlayTexture.NO_OVERLAY,
                        1.0f,
                        1.0f,
                        1.0f,
                        transparency);
            },
                    (pose) -> BOTIUtils.RenderScene(pose, exteriorTile));
//            exteriorTile.getFBOContainer().Render(exteriorTile, stack, 0xf000f0);
            stack.popPose();
        }

        stack.popPose();
    }
}
