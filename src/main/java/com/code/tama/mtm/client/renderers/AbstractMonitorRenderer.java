package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.MTMMod;
import com.code.tama.mtm.client.UI.ComponentTypes;
import com.code.tama.mtm.client.UI.UIComponent;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.tileentities.AbstractMonitorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AbstractMonitorRenderer<T extends AbstractMonitorTile> implements BlockEntityRenderer<T> {
    public final BlockEntityRendererProvider.Context context;
    public static final int fullBright = LightTexture.pack(15, 15);
    public final ArrayList<UIComponent> components = new ArrayList<>();

    public AbstractMonitorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.components.add(new UIComponent() {
            @Override
            public ComponentTypes Type() {
                return ComponentTypes.BUTTON;
            }

            @Override
            public Map<Axis, Float[]> XYBounds() {
                Map<Axis, Float[]> bounds = new HashMap<>();
                bounds.put(Axis.XP, new Float[] {0f, 1f});
                bounds.put(Axis.YP, new Float[] {0f, 1f});
                return bounds;
            }
        });
    }

    @Override
    public void render(@NotNull T monitor, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (monitor.getLevel() == null) return;

        monitor.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.98, 0.5);
            poseStack.scale(-0.011f, -0.011f, 0.011f);

            BlockState state = monitor.getBlockState();
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            switch (facing) {
                case NORTH -> poseStack.translate(0, 0, -Offset());
                case SOUTH -> poseStack.translate(0, 0, Offset());
                case WEST  -> poseStack.translate(Offset(), 0, 0);
                case EAST  -> poseStack.translate(-Offset(), 0, 0);
            }

            float yaw = switch (facing) {
                case NORTH -> 0;
                case SOUTH -> 180;
                case WEST -> -90;
                case EAST -> 90;
                default -> 0;
            };

            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

            ApplyCustomTransforms(poseStack, bufferSource);

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

    private void renderRotatingImage(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        ResourceLocation texture = new ResourceLocation(MTMMod.MODID, "textures/tiles/monitor/galifrayan.png");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderTexture(0, texture);

        float rotationAngle = (monitor.getLevel().getGameTime() % 360) + Minecraft.getInstance().getFrameTime();

        poseStack.translate(25, 70, 0);
        poseStack.scale(20, 20, 20);
        poseStack.mulPose(Axis.ZP.rotationDegrees(rotationAngle));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        buffer.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 0).endVertex();
        buffer.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 0).endVertex();
        buffer.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 1).endVertex();
        buffer.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 1).endVertex();

        BufferUploader.drawWithShader(buffer.end());
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
    }

    private void renderUIComponents(AbstractMonitorTile monitor, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight) {
        ResourceLocation texture = new ResourceLocation(MTMMod.MODID, "textures/tiles/monitor/galifrayan.png");
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderTexture(0, texture);


        poseStack.translate(25, 70, 0);
        poseStack.scale(20, 20, 20);
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        Matrix4f matrix = poseStack.last().pose();
        BufferBuilder buffer = Tesselator.getInstance().getBuilder();

        for (UIComponent component : this.components) {
            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

//            "?{"
            buffer.vertex(matrix, -0.5f, -0.5f, 0).uv(0, 0).endVertex();
            buffer.vertex(matrix, 0.5f, -0.5f, 0).uv(1, 0).endVertex();
            buffer.vertex(matrix, 0.5f, 0.5f, 0).uv(1, 1).endVertex();
            buffer.vertex(matrix, -0.5f, 0.5f, 0).uv(0, 1).endVertex();

            BufferUploader.drawWithShader(buffer.end());
        }
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
    }

    public float Offset() {
        return 44.3f;
    }

    public void ApplyCustomTransforms(PoseStack stack, MultiBufferSource bufferSource) {

    }
}