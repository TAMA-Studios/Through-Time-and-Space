/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;


import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.CapabilityConstants;
import com.code.tama.tts.server.tileentities.ChameleonCircuitPanelTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ChameleonCircuitRenderer implements BlockEntityRenderer<ChameleonCircuitPanelTileEntity> {
    public final BlockEntityRendererProvider.Context context;
    public static final int fullBright = 0xF000F0;//LightTexture.pack(15, 15);
    public Model MODEL;
    public String modelName = "";
    public AbstractJSONRenderer json;

    public ChameleonCircuitRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull ChameleonCircuitPanelTileEntity chameleonCircuit, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (chameleonCircuit.getLevel() == null) return;
        chameleonCircuit.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            cap.GetExteriorVariant().GetModelName();

            poseStack.pushPose();
            poseStack.translate(0.5, 0.52, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.scale(0.2f, 0.2f, 0.2f);

            assert Minecraft.getInstance().level != null;
            float time = Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getFrameTime();
            if (time % 10 < 2) {
                float glitchOffset = (Math.random() > 0.5) ? 0.1f : -0.1f;
                poseStack.translate(glitchOffset, 0, 0);
            }

            float flicker = 0.5f + 0.3f * (float) Math.sin(time * 0.1);
            float blueTintFactor = 0.2f;
            float r = 1.0f - blueTintFactor;
            float g = 1.0f - (blueTintFactor / 2);
            float b = 1.0f;


            if(this.json == null || this.MODEL == null || !this.modelName.equals(cap.GetExteriorModel().GetExteriorName())) {
                this.json = new AbstractJSONRenderer(cap.GetExteriorModel().GetModelName());
                this.MODEL = json.getModel();
                this.modelName = cap.GetExteriorModel().GetExteriorName();
            }

            poseStack.mulPose(Axis.YP.rotationDegrees((float) Minecraft.getInstance().level.getGameTime() % 360));

            this.MODEL.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(json.getTexture())),
                            fullBright, OverlayTexture.NO_OVERLAY,
                            r, g, b, flicker);

            poseStack.popPose();

            poseStack.pushPose();

            poseStack.translate(0.5, 1, 0.5);
            poseStack.scale(-0.02f, -0.02f, 0.02f);

            assert Minecraft.getInstance().player != null;
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