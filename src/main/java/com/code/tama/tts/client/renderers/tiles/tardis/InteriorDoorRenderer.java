/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.capabilities.Capabilities;
import com.code.tama.tts.server.tileentities.DoorTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;

public class InteriorDoorRenderer implements BlockEntityRenderer<DoorTile> {
	public InteriorDoorRenderer(BlockEntityRendererProvider.Context context) {
	}

	private static void renderDoor(JavaJSONRenderer door, @NotNull PoseStack poseStack, VertexConsumer bufferSource,
			int combinedLight) {
		door.render(poseStack, bufferSource, combinedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}

	@Override
	public void render(@NotNull DoorTile doorTile, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		assert doorTile.getLevel() != null;

		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(180));
		poseStack.translate(0.5, 0, 0);

		doorTile.getLevel().getCapability(Capabilities.TARDIS_LEVEL_CAPABILITY).ifPresent(cap -> {
			AbstractJSONRenderer renderer = cap.GetClientData().getExteriorRenderer();

			JavaJSONRenderer door = cap.GetClientData().getInteriorDoor();
			JavaJSONRenderer boti = cap.GetClientData().getInteriorBOTI();

			cap.GetClientData().setupInteriorDoorPose();

			assert Minecraft.getInstance().level != null;

			if (cap.GetFlightData().isInFlight()) {
				doorTile.getFBOContainer().Render(poseStack, (pose, buf) -> {
					renderDoor(boti, pose, buf.getBuffer(RenderType.solid()), 0xf000f0);
					// StencilUtils.drawColoredFrame(pose, 1, 2, new Vec3(0, 0, 0))
				}, (pose, buf) -> {
				}, (pose, buf) -> {
					pose.pushPose();
					pose.mulPose(
							Axis.ZP.rotationDegrees((float) Minecraft.getInstance().level.getGameTime() / 100 * 360f));
					pose.mulPose(Axis.YP.rotationDegrees(180));
					pose.translate(0, 0, 500);
					pose.scale(1.5f, 1.5f, 1.5f);
					cap.GetClientData().getVortex().renderVortex(pose);
					pose.popPose();
				});

				poseStack.translate(0, 0, -0.5);
			} else { // BOTI!!

			}
			renderDoor(door, poseStack,
					bufferSource.getBuffer(renderer.getRenderType(cap.GetData().getExteriorModel().getTexture())),
					combinedLight);
		});

		poseStack.popPose();
	}
}
