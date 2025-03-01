package com.code.tama.mtm.Client.Renderer;


import com.code.tama.mtm.Capabilities.CapabilityConstants;
import com.code.tama.mtm.Client.Models.ModernBoxModel;
import com.code.tama.mtm.TileEntities.ChameleonCircuitPanelTileEntity;
import com.code.tama.mtm.misc.ExteriorModels;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ExteriorSelector_Renderer implements BlockEntityRenderer<ChameleonCircuitPanelTileEntity> {
    public ModelPart MODEL;
    public final BlockEntityRendererProvider.Context context;
    public static ResourceLocation TEXTURE;

    public ExteriorSelector_Renderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
        this.MODEL = context.bakeLayer(ModernBoxModel.LAYER_LOCATION);
    }

    @Override
    public void render(@NotNull ChameleonCircuitPanelTileEntity chameleonCircuit, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if(chameleonCircuit.getLevel() == null) return;
        chameleonCircuit.getLevel().getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            this.MODEL = ExteriorModels.GetModel(this.context, cap.GetExteriorVariant().GetExteriorType());

            TEXTURE = cap.GetExteriorVariant().GetTexture();

            poseStack.pushPose();
            poseStack.mulPose(Axis.XP.rotationDegrees(180));
            poseStack.mulPose(Axis.YP.rotationDegrees(180));
            poseStack.translate(-0.5, -0.35, 0.5);
            poseStack.scale(0.07f, 0.07f, 0.07f);
            this.MODEL.yRot = (float) Math.toRadians(Minecraft.getInstance().level.getGameTime() % 360);

            this.MODEL.render(poseStack, bufferSource.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLight, OverlayTexture.NO_OVERLAY,
                    1.0f, 1.0f, 1.0f, 1);
            poseStack.popPose();
        });
    }

}
