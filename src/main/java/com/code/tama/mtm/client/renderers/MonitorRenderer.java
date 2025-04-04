package com.code.tama.mtm.client.renderers;


import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.tileentities.ChameleonCircuitPanelTileEntity;
import com.code.tama.mtm.server.tileentities.MonitorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class MonitorRenderer implements BlockEntityRenderer<MonitorTile> {
    public final BlockEntityRendererProvider.Context context;
    public static final int fullBright = LightTexture.pack(15, 15);

    // Removed the exterior model rendering code for ya maketendo

    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(@NotNull MonitorTile monitor, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (monitor.getLevel() == null) return;
        monitor.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            poseStack.pushPose();
            poseStack.translate(0.5, 1, 0.5);
            poseStack.scale(-0.02f, -0.02f, 0.02f);

            float yaw = Minecraft.getInstance().player.getYRot();
            poseStack.mulPose(Axis.YP.rotationDegrees(yaw));

            Font fontRenderer = Minecraft.getInstance().font;
            String text = cap.GetExteriorVariant().GetExteriorName();

            fontRenderer.drawInBatch(text, -fontRenderer.width(text) / 2f, 0, 0x55AAFF, true,
                    poseStack.last().pose(), bufferSource, Font.DisplayMode.SEE_THROUGH, 0, fullBright);

            poseStack.popPose();
        });
    }
}