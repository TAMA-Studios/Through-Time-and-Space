/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.triggerapi.StencilUtils;
import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.client.renderers.HalfBOTIRenderer;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.mixin.client.IMinecraftAccessor;
import com.code.tama.tts.server.blocks.ExteriorBlock;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
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

        parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(Math.max(data.FrameLeft * 13.333, 0));
        parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-Math.max(data.FrameRight * 13.333, 0));

        ModelPart boti = parsed.getPart("BOTI").modelPart;
        ModelPart partialBOTI = parsed.getPart("PartialBOTI").modelPart;

//        parsed.getPart("baseRoot").render(stack, bufferSource.getBuffer(ext.getRenderType()), combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);
//        ext.render(exteriorTile, 0, stack, bufferSource, combinedLight, OverlayTexture.NO_OVERLAY);



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
            stack.translate(0, 0, 0.5);
            exteriorTile.getFBOContainer().Render(stack, (pose, botiSource) -> {
                        pose.pushPose();
                        pose.translate(0, 1.5, 0);
                        boti.render(stack, botiSource.getBuffer(RenderType.solid()), 0xf000f0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
                        if (true) // TODO: CONFIG FOR PARTIAL BOTI!
                            partialBOTI.render(stack, botiSource.getBuffer(RenderType.solid()), 0xf000f0, OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
                        pose.popPose();
                    },
                    (pose, buffer) -> {},
                    (pose, botiSource) -> {
                        // TODO: SKY RENDERER!!!
                        pose.pushPose();
                        pose.scale(2, 4, 2);
                        if (exteriorTile.SkyColor == null || (Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 1) % 1200 == 0) {
                            if (exteriorTile.type != null) {
                                Minecraft mc = Minecraft.getInstance();
                                ClientLevel oldLevel = mc.level;
                                assert mc.level != null;
                                Holder<DimensionType> dimType = mc.level.registryAccess()
                                        .registryOrThrow(Registries.DIMENSION_TYPE)
                                        .getHolderOrThrow(exteriorTile.dimensionTypeId);

                                LevelRenderer renderer = new LevelRenderer(mc, mc.getEntityRenderDispatcher(), mc.getBlockEntityRenderDispatcher(), mc.renderBuffers());
                                ClientLevel level = new ClientLevel(mc.player.connection, mc.level.getLevelData(), exteriorTile.targetLevel, dimType, mc.options.getEffectiveRenderDistance(), mc.options.getEffectiveRenderDistance(), mc.level.getProfilerSupplier(), renderer, false, 0);
                                renderer.setLevel(level);

                                mc.level = level;
                                exteriorTile.SkyColor = Minecraft.getInstance().level.getSkyColor(exteriorTile.targetPos.getCenter(), ((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
                                mc.level = oldLevel;
                            } else
                                exteriorTile.SkyColor = Minecraft.getInstance().level.getSkyColor(Minecraft.getInstance().player.position(), ((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
                        }
                        StencilUtils.drawColoredCube(stack, 1, exteriorTile.SkyColor);
                        botiSource.endBatch();
                        pose.popPose();
                        pose.pushPose();
                        pose.translate(-0.5, -0.5, -0.5);
                        BOTIUtils.RenderScene(pose, exteriorTile);
                        pose.popPose();
                    });
            stack.popPose();
        }

        parsed.renderToBuffer(
                stack,
                bufferSource.getBuffer(ext.getRenderType()),
                combinedLight,
                OverlayTexture.NO_OVERLAY,
                1.0f,
                1.0f,
                1.0f,
                transparency);

        stack.popPose();
    }
}
