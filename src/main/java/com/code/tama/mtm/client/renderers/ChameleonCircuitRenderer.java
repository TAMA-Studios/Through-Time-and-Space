package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.core.abstractClasses.HierarchicalExteriorModel;
import com.code.tama.mtm.core.interfaces.IUseExteriorModels;
import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.tileentities.ChameleonCircuitPanelTileEntity;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class ChameleonCircuitRenderer<T extends ExteriorTile, C extends HierarchicalExteriorModel> extends IUseExteriorModels implements BlockEntityRenderer<ChameleonCircuitPanelTileEntity> {
    public HierarchicalExteriorModel MODEL;
    public final BlockEntityRendererProvider.Context context;
    public static final int fullBright = LightTexture.pack(15, 15);

    public ChameleonCircuitRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void render(@NotNull ChameleonCircuitPanelTileEntity chameleonCircuit, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (chameleonCircuit.getLevel() == null) return;
        if(this.getHandler().InstanceModels.isEmpty()) return;
        chameleonCircuit.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            this.MODEL = this.getHandler().InstanceModels.get(cap.GetExteriorModelIndex());

            poseStack.pushPose();
            poseStack.translate(0.6, 0.1, 0.6);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.scale(0.2f, 0.2f, 0.2f);

            float time = Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getFrameTime();
            if (time % 10 < 2) {
                float glitchOffset = (Math.random() > 0.5) ? 0.1f : -0.1f;
                poseStack.translate(glitchOffset, 0, 0);
            }

            this.MODEL.root().yRot = (float) Math.toRadians(Minecraft.getInstance().level.getGameTime() % 360);

            float flicker = 0.5f + 0.3f * (float) Math.sin(time * 0.1);
            float blueTintFactor = 0.2f;
            float r = 1.0f - blueTintFactor;
            float g = 1.0f - (blueTintFactor / 2);
            float b = 1.0f;

            this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucentCull(cap.GetExteriorVariant().GetTexture())),
                    fullBright, OverlayTexture.NO_OVERLAY,
                    r, g, b, flicker);

//            this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucentCull(cap.GetExteriorVariant().GetEmmisiveTexture())),
//                    fullBright, OverlayTexture.NO_OVERLAY,
//                    r, g, b, flicker);

            poseStack.popPose();

            poseStack.pushPose();

            poseStack.translate(0.5, 1, 0.5);
            poseStack.scale(-0.02f, -0.02f, 0.02f);

            float yaw = Minecraft.getInstance().player.getYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

            Font fontRenderer = Minecraft.getInstance().font;
            String text = cap.GetExteriorVariant().GetExteriorName();

            fontRenderer.drawInBatch(text, -fontRenderer.width(text) / 2f, 0, 0x55AAFF, true,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.NORMAL, 0, fullBright);

            poseStack.popPose();
        });
    }
}