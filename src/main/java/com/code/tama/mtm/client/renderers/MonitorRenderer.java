package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.MTMMod;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.tileentities.MonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.Locale;

public class MonitorRenderer implements BlockEntityRenderer<MonitorTile> {
    public final BlockEntityRendererProvider.Context context;
    public static final int fullBright = LightTexture.pack(15, 15);

    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull MonitorTile monitor, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (monitor.getLevel() == null) return;

        monitor.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.98, 0.5);
            poseStack.scale(-0.011f, -0.011f, 0.011f);

            BlockState state = monitor.getBlockState();
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            double offset = 44.3f;
            switch (facing) {
                case NORTH -> poseStack.translate(0, 0, -offset);
                case SOUTH -> poseStack.translate(0, 0, offset);
                case WEST  -> poseStack.translate(offset, 0, 0);
                case EAST  -> poseStack.translate(-offset, 0, 0);
            }

            float yaw = switch (facing) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> -90;
                case EAST -> 90;
                default -> 0;
            };

            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

            Font fontRenderer = Minecraft.getInstance().font;

            String line1 = cap.GetCurrentLevel().location().getPath()
                    .substring(0, 1).toUpperCase(Locale.ROOT)
                    + cap.GetCurrentLevel().location().getPath().substring(1).replace("_", " ");

            String line2 = cap.GetExteriorLocation().ReadableStringShort();
            String line3 = cap.GetDestination().ReadableStringShort();

            int white = 0xFFFFFF;

            RenderSystem.disableDepthTest();

            fontRenderer.drawInBatch("TARDISOS - 1.0",  -40, 5, white, false,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, combinedLight);

            fontRenderer.drawInBatch(line1,  -40, 15, white, false,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, combinedLight);

            fontRenderer.drawInBatch(line2, -40, 25, white, false,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, combinedLight);

            fontRenderer.drawInBatch(line3, -40, 35, white, false,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, combinedLight);

            renderRotatingImage(monitor, poseStack, bufferSource, combinedLight);

            RenderSystem.enableDepthTest();

            poseStack.popPose();
        });
    }

    private void renderRotatingImage(MonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        ResourceLocation texture = new ResourceLocation(MTMMod.MODID, "textures/tiles/monitor/galifrayan.png");
        RenderSystem.setShaderTexture(0, texture);

        float rotationAngle = (monitor.getLevel().getGameTime() % 360) + Minecraft.getInstance().getFrameTime();

        poseStack.pushPose();
        poseStack.translate(0, 1, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 0).endVertex();
        buffer.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 1).endVertex();
        buffer.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 1).endVertex();

        BufferUploader.drawWithShader(buffer.end());

        poseStack.popPose();
    }
}