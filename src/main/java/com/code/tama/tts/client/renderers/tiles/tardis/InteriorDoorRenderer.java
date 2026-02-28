/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;
import com.code.tama.triggerapi.boti.AbstractPortalTile;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.helpers.rendering.StencilUtils;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.mixin.client.IMinecraftAccessor;
import com.code.tama.tts.server.tileentities.DoorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.NotNull;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class InteriorDoorRenderer implements BlockEntityRenderer<DoorTile> {

	// Door animation constants — keep in sync with TardisExteriorRenderer
	private static final float DOOR_MAX     = 5.625f;
	private static final float DOOR_SPEED   = 0.15f;
	private static final float DOOR_MAX_DEG = 75f;

	// Per-instance animation state — right opens on DoorsOpen() >= 1, left on == 2
	private float doorFrameRight = 0f;
	private float doorFrameLeft  = 0f;
	private float frameTimeO     = Float.NaN;

	public InteriorDoorRenderer(BlockEntityRendererProvider.Context context) {
	}

	/** Smoothstep ease-in + ease-out. t is 0.0–1.0, output is 0.0–1.0. */
	private static float easing(float t) {
		return (float)((1.0 - Math.cos(t * Math.PI)) / 2.0);
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
		GetTARDISCapSupplier(doorTile.getLevel()).ifPresent(cap -> {
			AbstractJSONRenderer renderer = cap.GetClientData().getExteriorRenderer();

			JavaJSONRenderer door = cap.GetClientData().getInteriorDoor();
			JavaJSONRenderer boti = cap.GetClientData().getInteriorBOTI();

			// Door angles are applied below after the counter tick

			assert Minecraft.getInstance().level != null;

			// ---- Tick door counters once per frame ----
			if (frameTimeO != partialTicks) {
				frameTimeO = partialTicks;
				int doorsOpen = cap.GetData().getDoorData().getDoorsOpen();
				if (doorsOpen >= 1)
					doorFrameRight = Math.min(doorFrameRight + DOOR_SPEED, DOOR_MAX);
				else
					doorFrameRight = Math.max(doorFrameRight - DOOR_SPEED, 0f);
				if (doorsOpen == 2)
					doorFrameLeft = Math.min(doorFrameLeft + DOOR_SPEED, DOOR_MAX);
				else
					doorFrameLeft = Math.max(doorFrameLeft - DOOR_SPEED, 0f);
			}

			float rightAngle = easing(doorFrameRight / DOOR_MAX) * DOOR_MAX_DEG;
			float leftAngle  = easing(doorFrameLeft  / DOOR_MAX) * DOOR_MAX_DEG;

			doorTile.getFBOContainer().Render(poseStack, (pose, buf) -> {
				pose.pushPose();
				pose.translate(0.5, 2.2, 1);
				renderBone(boti, pose, buf.getBuffer(RenderType.solid()), 0xf000f0);
				pose.popPose();
			}, (pose, buf) -> {
			}, (pose, buf) -> {
				if (cap.GetFlightData().isInFlight() || cap.GetFlightData().IsTakingOff()) {
					pose.pushPose();
					if (cap.GetFlightData().IsTakingOff()) {
						double transparency = Math.sin((double)(Minecraft.getInstance().level.getGameTime() % 40));
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
					pose.pushPose();
					pose.translate(0, 0, 1.3);
					renderBOTI(pose, doorTile, buf);
					pose.popPose();
				}
				RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
			});

			poseStack.mulPose(Axis.XP.rotationDegrees(180));
			poseStack.mulPose(Axis.YP.rotationDegrees(180));
			poseStack.translate(-0.5, 0.15, 0.5);

			cap.GetClientData().getExteriorRenderer().getJavaJSON()
					.getPart("IntRightDoor").yRot = (float) Math.toRadians(-rightAngle);
			cap.GetClientData().getExteriorRenderer().getJavaJSON()
					.getPart("IntLeftDoor").yRot  = (float) Math.toRadians( leftAngle);
			renderBone(door, poseStack,
					bufferSource.getBuffer(renderer.getRenderType(cap.GetData().getExteriorModel().getTexture())),
					combinedLight);
		});

		poseStack.popPose();
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
		if (portal.SkyColor == null
				|| (Minecraft.getInstance().level != null ? Minecraft.getInstance().level.getGameTime() : 1) % 1200 == 0) {
			if (portal.type != null) {
				Minecraft mc = Minecraft.getInstance();
				ClientLevel oldLevel = mc.level;
				assert mc.level != null;
				Holder<DimensionType> dimType = mc.level.registryAccess()
						.registryOrThrow(Registries.DIMENSION_TYPE)
						.getHolderOrThrow(portal.dimensionTypeId);
				LevelRenderer renderer = new LevelRenderer(mc, mc.getEntityRenderDispatcher(),
						mc.getBlockEntityRenderDispatcher(), mc.renderBuffers());
				assert mc.player != null;
				ClientLevel level = new ClientLevel(mc.player.connection, mc.level.getLevelData(),
						portal.targetLevel, dimType, mc.options.getEffectiveRenderDistance(),
						mc.options.getEffectiveRenderDistance(), mc.level.getProfilerSupplier(), renderer, false, 0);
				renderer.setLevel(level);
				mc.level = level;
				portal.SkyColor = Minecraft.getInstance().level.getSkyColor(portal.targetPos.getCenter(),
						((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
				mc.level = oldLevel;
			} else {
				assert Minecraft.getInstance().player != null;
				assert Minecraft.getInstance().level != null;
				portal.SkyColor = Minecraft.getInstance().level.getSkyColor(
						Minecraft.getInstance().player.position(),
						((IMinecraftAccessor) Minecraft.getInstance()).getTimer().partialTick);
			}
		}

		StencilUtils.drawColoredFrame(pose, 2, 4, portal.SkyColor);
		botiSource.endBatch();

		pose.popPose();
	}
}