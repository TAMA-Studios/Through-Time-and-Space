/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.core.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.core.tileentities.DoorTile;
import com.mojang.blaze3d.systems.RenderSystem;
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
import com.code.tama.triggerapi.animation.GeoAnimTicker;
import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;

public class InteriorDoorRenderer implements BlockEntityRenderer<DoorTile> {
	private static long lastTicks = -1;
	// Door animation constants, keep in sync with TardisExteriorRenderer
	private static final float DOOR_MAX = 5.625f;
	private static final float DOOR_SPEED = 0.15f;
	private static final float DOOR_MAX_DEG = 75f;

	// Per-instance animation state, right opens on DoorsOpen() >= 1, left on == 2
	private float doorFrameRight = 0f;
	private float doorFrameLeft = 0f;
	private float frameTimeO = Float.NaN;

	public InteriorDoorRenderer(BlockEntityRendererProvider.Context context) {
	}

	/** Smoothstep ease-in + ease-out. t is 0.0–1.0, output is 0.0–1.0. */
	private static float easing(float t) {
		return (float) ((1.0 - Math.cos(t * Math.PI)) / 2.0);
	}

	private static void renderBone(JavaJSONRenderer bone, @NotNull PoseStack poseStack, VertexConsumer bufferSource,
			int combinedLight) {
		bone.render(poseStack, bufferSource, combinedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
	}

	@Override
	public void render(@NotNull DoorTile doorTile, float partialTicks, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {

		assert doorTile.getLevel() != null;

		poseStack.pushPose();
		if (doorTile.getLevel() != null) {
			if (doorTile.getBlockState().getBlock() instanceof ExteriorBlock)
				poseStack.mulPose(doorTile.getBlockState().getValue(ExteriorBlock.FACING).getOpposite().getRotation());

			// stack.mulPose(Axis.YP.rotationDegrees(180));
			// // stack.mulPose(Axis.XN.rotationDegrees(90));
			// stack.mulPose(Axis.ZN.rotationDegrees(180));
		}
		GetTARDISCapSupplier(doorTile.getLevel()).ifPresent(cap -> {
			AbstractJSONRenderer renderer = cap.GetClientData().getExteriorRenderer();

			JavaJSONRenderer door = cap.GetClientData().getInteriorDoors();
			JavaJSONRenderer frame = cap.GetClientData().getInteriorDoorFrame();
			JavaJSONRenderer boti = cap.GetClientData().getInteriorBOTI();

			// Door angles are applied below after the counter tick

			assert Minecraft.getInstance().level != null;

			// ---- Tick door counters once per frame ----
			if (frameTimeO != partialTicks) {
				frameTimeO = partialTicks;
				int doorsOpen = cap.GetData().getDoorData().getDoorsOpen();
				if (doorsOpen == 2)
					doorFrameRight = Math.min(doorFrameRight + DOOR_SPEED, DOOR_MAX);
				else
					doorFrameRight = Math.max(doorFrameRight - DOOR_SPEED, 0f);
				if (doorsOpen >= 1)
					doorFrameLeft = Math.min(doorFrameLeft + DOOR_SPEED, DOOR_MAX);
				else
					doorFrameLeft = Math.max(doorFrameLeft - DOOR_SPEED, 0f);
			}

			float rightAngle = easing(doorFrameRight / DOOR_MAX) * DOOR_MAX_DEG;
			float leftAngle = easing(doorFrameLeft / DOOR_MAX) * DOOR_MAX_DEG;

			doorTile.getFBOContainer().Render(poseStack, (pose, buf) -> {
				pose.pushPose();
				pose.translate(0.5, 2.2, 1);
				pose.scale(door.model.modelScale, door.model.modelScale, door.model.modelScale);
				renderBone(boti, pose, buf.getBuffer(RenderType.solid()), 0xf000f0);
				buf.endBatch();
				pose.popPose();

				pose.popPose();
				pose.pushPose();

				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				poseStack.translate(-0.5, -.001, 1); // The .001 on the Y is to move it JUST above the ground for Z
														// fighting

				// Set bone rotations directly on the model, rotating the pose stack would
				// swing the entire frame. These bones are on the exterior renderer's JSON,
				// matching what setupInteriorDoorPose() does, but with eased angles.
				poseStack.scale(door.model.modelScale, door.model.modelScale, door.model.modelScale);
				cap.GetClientData().getExteriorRenderer().getJavaJSON().getPart("IntRightDoor").yRot = (float) Math
						.toRadians(rightAngle);
				cap.GetClientData().getExteriorRenderer().getJavaJSON().getPart("IntLeftDoor").yRot = (float) Math
						.toRadians(-leftAngle);

				RenderSystem.disableDepthTest();

				renderBone(door, poseStack,
						bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getTexture())),
						combinedLight);

				if (renderer.getLightMap() != null)
					renderBone(door, poseStack,
							bufferSource
									.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getLightMap())),
							combinedLight);

				((MultiBufferSource.BufferSource) bufferSource).endBatch();
				RenderSystem.enableDepthTest();
				pose.popPose();
				pose.pushPose();
			}, (pose, buf) -> {
			}, (pose, buf) -> {
				pose.pushPose();
				if (cap.GetFlightData().isInFlight() || cap.GetFlightData().IsTakingOff()) {
					pose.pushPose();
					if (cap.GetFlightData().IsTakingOff()) {
						if (lastTicks == -1)
							lastTicks = GeoAnimTicker.getTicks();
						double transparency = landFadeAnimation(lastTicks);
						RenderSystem.setShaderColor(1F, 1F, 1F, (float) transparency);
					}
					pose.mulPose(
							Axis.ZP.rotationDegrees((float) Minecraft.getInstance().level.getGameTime() / 100 * 360f));
					pose.mulPose(Axis.YP.rotationDegrees(180));
					pose.translate(0, 0, 500);
					pose.scale(1.5f, 1.5f, 1.5f);
					cap.GetClientData().getVortex().renderVortex(pose);
					pose.popPose();
				} else {
					lastTicks = -1;
					pose.pushPose();
					pose.translate(0, 0, 1.4);
					renderBOTI(pose, doorTile, buf);
					pose.popPose();
				}
				buf.endBatch();
				pose.popPose();

				RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

				pose.popPose();
				pose.pushPose();

				poseStack.mulPose(Axis.XP.rotationDegrees(180));
				poseStack.mulPose(Axis.YP.rotationDegrees(180));
				poseStack.translate(-0.5, -.001, 1); // The .001 on the Y is to move it JUST above the ground for Z
														// fighting

				// Set bone rotations directly on the model, rotating the pose stack would
				// swing the entire frame. These bones are on the exterior renderer's JSON,
				// matching what setupInteriorDoorPose() does, but with eased angles.
				poseStack.scale(door.model.modelScale, door.model.modelScale, door.model.modelScale);
				cap.GetClientData().getExteriorRenderer().getJavaJSON().getPart("IntRightDoor").yRot = (float) Math
						.toRadians(rightAngle);
				cap.GetClientData().getExteriorRenderer().getJavaJSON().getPart("IntLeftDoor").yRot = (float) Math
						.toRadians(-leftAngle);

				RenderSystem.disableDepthTest();

				renderBone(door, poseStack,
						bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getTexture())),
						combinedLight);

				if (renderer.getLightMap() != null)
					renderBone(door, poseStack,
							bufferSource
									.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getLightMap())),
							combinedLight);

				((MultiBufferSource.BufferSource) bufferSource).endBatch();
				RenderSystem.enableDepthTest();
				pose.popPose();
				pose.pushPose();
			});

			poseStack.mulPose(Axis.XP.rotationDegrees(180));
			poseStack.mulPose(Axis.YP.rotationDegrees(180));
			poseStack.translate(-0.5, -.001, 0.5); // The .001 on the Y is to move it JUST above the ground for Z
													// fighting

			// Set bone rotations directly on the model, rotating the pose stack would
			// swing the entire frame. These bones are on the exterior renderer's JSON,
			// matching what setupInteriorDoorPose() does, but with eased angles.
			poseStack.scale(door.model.modelScale, door.model.modelScale, door.model.modelScale);
			renderBone(frame, poseStack,
					bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getTexture())),
					combinedLight);

			renderBone(door, poseStack,
					bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getTexture())),
					combinedLight);

			if (renderer.getLightMap() != null) {
				renderBone(frame, poseStack,
						bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getLightMap())),
						combinedLight);

				renderBone(door, poseStack,
						bufferSource.getBuffer(renderer.getRenderType(cap.GetClientData().getExterior().getLightMap())),
						combinedLight);
			}
		});

		poseStack.popPose();
	}

	private float landFadeAnimation(long startTick) {
		float base = 1.0f;
		float initialAmp = 1.0f;
		float decay = 0.05f;
		float freq = 0.3f;

		float tick = (GeoAnimTicker.getTicks() - startTick) / 5;

		float amp = (float) (initialAmp * Math.exp(-decay * tick));
		float alpha = base - amp * (float) Math.abs(Math.sin(freq * tick));

		return alpha;
	}

	public void renderBOTI(PoseStack pose, AbstractPortalTile portal, MultiBufferSource.BufferSource botiSource) {
		pose.pushPose();
		renderSky(portal, pose, botiSource);
		pose.popPose();

		pose.pushPose();
		pose.translate(1.5, -0.5, -0.5);
		BOTIUtils.RenderScene(pose, portal);
		pose.popPose();
	}

	public static void renderSky(AbstractPortalTile portal, PoseStack pose, MultiBufferSource.BufferSource botiSource) {
		pose.pushPose();
		pose.scale(2, 4, 2);

		// Update sky color every 20 seconds or when null

		StencilUtils.drawColoredFrame(pose, 2, 4, portal.SkyColor);
		botiSource.endBatch();

		pose.popPose();
	}
}