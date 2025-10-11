/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONParsed;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;
import com.code.tama.tts.client.models.ColinRichmondInteriorDoors;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.DoorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class InteriorDoorRenderer implements BlockEntityRenderer<DoorTile> {
    public final ModelPart MODEL;

    public InteriorDoorRenderer(BlockEntityRendererProvider.Context context) {
        this.MODEL = context.bakeLayer(ColinRichmondInteriorDoors.LAYER_LOCATION);
    }

    @Override
    public void render(
            @NotNull DoorTile doorTile,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource bufferSource,
            int combinedLight,
            int combinedOverlay) {

        assert doorTile.getLevel() != null;

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.mulPose(Axis.YP.rotationDegrees(180));
        //        poseStack.translate(-0.5, -0.645, 0.33);
        //        poseStack.scale(0.43f, 0.43f, 0.43f);
        //        this.MODEL.yRot = (float) Math.toRadians(180.0);

        poseStack.translate(-0.5, 0, 0.5);
        doorTile.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            AbstractJSONRenderer renderer =
                    new AbstractJSONRenderer(cap.GetData().getExteriorModel().getModel());
            JavaJSONParsed parsed = renderer.getJavaJSON();

            JavaJSONRenderer door = parsed.getPart("InteriorDoor");

            parsed.getPart("IntRightDoor").yRot =
                    (float) (cap.GetData().getDoorData().getDoorsOpen() == 2 ? Math.toRadians(90) : Math.toRadians(0));
            parsed.getPart("IntLeftDoor").yRot =
                    (float) (cap.GetData().getDoorData().getDoorsOpen() >= 1 ? -Math.toRadians(90) : Math.toRadians(0));

            door.render(
                    poseStack,
                    bufferSource.getBuffer(renderer.getRenderType()),
                    combinedLight,
                    OverlayTexture.NO_OVERLAY,
                    1,
                    1,
                    1,
                    1);
        });
        //        this.MODEL.render(
        //                poseStack,
        //                bufferSource.getBuffer(RenderType.debugFilledBox()),
        //                combinedLight,
        //                OverlayTexture.NO_OVERLAY,
        //                1.0f,
        //                1.0f,
        //                1.0f,
        //                1.0f);
        poseStack.popPose();
    }
}
