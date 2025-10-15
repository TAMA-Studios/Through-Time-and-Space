/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONParsed;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;
import com.code.tama.triggerapi.rendering.FBOHelper;
import com.code.tama.triggerapi.rendering.VortexRenderer;
import com.code.tama.tts.client.models.ColinRichmondInteriorDoors;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.DoorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class InteriorDoorRenderer implements BlockEntityRenderer<DoorTile> {
    public final ModelPart MODEL;
    FBOHelper helper = new FBOHelper();

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
        poseStack.translate(0.5, 0, 0);

        doorTile.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
            AbstractJSONRenderer renderer =
                    new AbstractJSONRenderer(cap.GetData().getExteriorModel().getModel());
            JavaJSONParsed parsed = renderer.getJavaJSON();

            JavaJSONRenderer door = parsed.getPart("InteriorDoor");
            JavaJSONRenderer boti = parsed.getPart("InteriorBOTI");

            parsed.getPart("IntRightDoor").yRot =
                    (float) (cap.GetData().getDoorData().getDoorsOpen() == 2 ? -Math.toRadians(90) : Math.toRadians(0));
            parsed.getPart("IntLeftDoor").yRot =
                    (float) (cap.GetData().getDoorData().getDoorsOpen() >= 1 ? Math.toRadians(90) : Math.toRadians(0));

            assert Minecraft.getInstance().level != null;
            if (cap.GetFlightData().isInFlight()) {
                helper.Render(
                        poseStack,
                        (pose, buf) -> {
                            boti.render(
                                    pose,
                                    buf.getBuffer(RenderType.solid()),
                                    0xf000f0,
                                    OverlayTexture.NO_OVERLAY,
                                    0,
                                    0,
                                    0,
                                    0);
                            //          StencilUtils.drawColoredFrame(pose, 1, 2, new Vec3(0, 0, 0))
                        },
                        (pose, buf) -> {},
                        (pose, buf) -> {
                            pose.pushPose();
                            pose.mulPose(Axis.ZP.rotationDegrees(
                                    (float) Minecraft.getInstance().level.getGameTime() / 100 * 360f));
                            pose.mulPose(Axis.YP.rotationDegrees(180));
                            pose.translate(0, 0, 500);
                            pose.scale(1.5f, 1.5f, 1.5f);
                            cap.GetClientData().getVortex().renderVortex(pose);
                            cap.GetClientData().getVortex().renderVortexLayer(pose, VortexRenderer.LayerType.SECOND);
                            cap.GetClientData().getVortex().renderVortexLayer(pose, VortexRenderer.LayerType.THIRD);
                            pose.popPose();
                        });

                poseStack.translate(0, 0, -0.5);
            }
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

        poseStack.popPose();
    }
}
