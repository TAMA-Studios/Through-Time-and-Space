/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;


import static com.code.tama.tts.TTSMod.MODID;

import com.code.tama.triggerapi.JavaInJSON.IUseJavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONParsed;
import com.code.tama.tts.server.tileentities.HartnellDoorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HartnellDoorRenderer implements BlockEntityRenderer<HartnellDoorTile>, IUseJavaJSON {
    boolean OldState = false;
    double Frame = 0;
    float OldFrame = 0;


    public HartnellDoorRenderer(BlockEntityRendererProvider.Context context) {
        this.registerJavaJSON(new ResourceLocation(MODID, "models/tile/hartnell_door.json"));
    }

    @Override
    public void render(@NotNull HartnellDoorTile example, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        poseStack.pushPose();
        poseStack.translate(1.5, 1.5, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        JavaJSONParsed parsed = JavaJSON.getParsedJavaJSON(this);

        if(this.OldState) {
            if(this.OldFrame != partialTicks) {
                if(this.Frame < 22.5)
                    this.Frame++;
                this.OldFrame = partialTicks;
            }
        }
        else {
            if(this.OldFrame != partialTicks) {
                if(this.Frame > 0)
                    this.Frame--;
                this.OldFrame = partialTicks;
            }
        }


        parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(-Math.max(Frame * 3.5, 0));
        parsed.getPart("RightDoor").yRot = (float) Math.toRadians(Math.max(Frame * 3.5, 0));
        parsed.getModelInfo().getModel().renderToBuffer(poseStack, bufferSource.getBuffer(this.getRenderType()), combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        poseStack.popPose();

        this.OldState = example.IsOpen();
    }
}