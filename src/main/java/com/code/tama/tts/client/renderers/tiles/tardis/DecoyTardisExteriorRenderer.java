/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.core.blocks.tardis.DecoyExteriorBlock;
import com.code.tama.tts.core.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.core.tileentities.DecoyExteriorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class DecoyTardisExteriorRenderer<T extends DecoyExteriorTile> implements BlockEntityRenderer<T> {

	// Door animation constants -- tweak these to taste
	private static final float DOOR_MAX = 5.625f; // counter range 0 → this
	private static final float DOOR_SPEED = 0.10f; // counter units per frame (~37 frames = ~1.8s)
	// (What the fuck was I on when I did that math... at 60fps 37 frames is roughly
	// half a second)
	private static final float DOOR_MAX_DEG = 75f; // max rotation in degrees when fully open

	public DecoyTardisExteriorRenderer(BlockEntityRendererProvider.Context context) {
	}

	public DecoyTardisExteriorRenderer() {
	}

	/**
	 * Smoothstep easing: ease-in AND ease-out. Input t is 0.0–1.0, output is
	 * 0.0–1.0. Accelerates off the latch, decelerates into the stop.
	 *
	 * TODO: Consider Swapping the body for: (float) Math.sin(t * Math.PI / 2) for
	 * faster start, gradual stop. See how that looks. Maybe.
	 */
	private static float easing(float t) {
		return (float) ((1.0 - Math.cos(t * Math.PI)) / 2.0);
	}

	@Override
	public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack stack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (exteriorTile.Model == null || exteriorTile.getLevel() != null
				&& exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos()).getBlock().equals(Blocks.AIR))
			return;

		ExteriorAnimationData data = exteriorTile.exteriorAnimationData;

		// ---- Tick door counters once per frame ----
		if (data.FrameTimeO != partialTicks) {
			data.FrameTimeO = partialTicks;

			// Right door: opens on DoorsOpen() >= 1
			if (exteriorTile.DoorsOpen() > 0)
				data.FrameRight = Math.min(data.FrameRight + DOOR_SPEED, DOOR_MAX);
			else
				data.FrameRight = Math.max(data.FrameRight - DOOR_SPEED, 0f);

			// Left door: opens on DoorsOpen() == 2
			if (exteriorTile.DoorsOpen() == 2)
				data.FrameLeft = Math.min(data.FrameLeft + DOOR_SPEED, DOOR_MAX);
			else
				data.FrameLeft = Math.max(data.FrameLeft - DOOR_SPEED, 0f);
		}

		// Normalize 0–DOOR_MAX to 0.0–1.0, run through curve, scale to degrees
		// float leftAngle = easing(data.FrameLeft / DOOR_MAX) *
		// exteriorTile.Model.getMaxDoorDeg();
		// float rightAngle = easing(data.FrameRight / DOOR_MAX) *
		// exteriorTile.Model.getMaxDoorDeg();

		stack.pushPose();
		float offs;
		if (exteriorTile.getLevel() != null)
			offs = -BlockUtils.getReverseHeightModifier(
					exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos().below()));
		else
			offs = 0;
		stack.translate(0.5, offs + 1.5, 0.5);

		if (exteriorTile.getLevel() != null) {
			if (exteriorTile.getBlockState().getBlock() instanceof DecoyExteriorBlock)
				stack.mulPose(exteriorTile.getBlockState().getValue(ExteriorBlock.FACING).getOpposite().getRotation());

			// stack.mulPose(exteriorTile.getFacing().getRotation());
			stack.mulPose(Axis.YP.rotationDegrees(180));
			stack.mulPose(Axis.XN.rotationDegrees(90));
			// stack.mulPose(Axis.ZN.rotationDegrees(180));
		}

		AbstractJSONRenderer ext = new AbstractJSONRenderer(exteriorTile.getModel().getModel());
		JavaJSONModel parsed = JavaJSON.getParsedJavaJSON(ext).getModelInfo().getModel();

		// parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(leftAngle);
		// parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-rightAngle);

		float maxDeg = exteriorTile.Model.getMaxDoorDeg();

		// Left/right symmetry is a fixed ±1, door direction comes from maxDeg's sign
		parsed.getPart("LeftDoor").yRot = (float) Math.toRadians(easing(data.FrameLeft / DOOR_MAX) * maxDeg);
		parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-easing(data.FrameRight / DOOR_MAX) * maxDeg);

		renderDoors(exteriorTile, stack, bufferSource, combinedLight, stack, parsed, ext, 1.0f);

		stack.translate(0, 1.5, 0);

		stack.scale(parsed.modelScale, parsed.modelScale, parsed.modelScale);
		parsed.getPart("baseRoot").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getTexture())), combinedLight,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		parsed.getPart("baseRoot").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getLightMap())), 0xf000f0,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

		((MultiBufferSource.BufferSource) bufferSource).endBatch();

		stack.popPose();
	}

	private static <T extends DecoyExteriorTile> void renderDoors(@NotNull T exteriorTile, @NotNull PoseStack stack,
			@NotNull MultiBufferSource bufferSource, int combinedLight, PoseStack pose, JavaJSONModel parsed,
			AbstractJSONRenderer ext, float transparency) {
		pose.pushPose();
		pose.translate(0, 1.5, 0);
		RenderSystem.disableDepthTest();

		pose.scale(parsed.modelScale, parsed.modelScale, parsed.modelScale);

		parsed.getPart("Doors").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getTexture())), combinedLight,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);

		parsed.getPart("Doors").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getLightMap())), 0xf000f0,
				OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);

		RenderSystem.enableDepthTest();
		pose.popPose();
	}
}