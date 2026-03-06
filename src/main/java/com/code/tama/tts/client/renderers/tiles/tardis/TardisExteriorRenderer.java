/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles.tardis;

import com.code.tama.triggerapi.JavaInJSON.JavaJSON;
import com.code.tama.triggerapi.JavaInJSON.JavaJSONModel;
import com.code.tama.triggerapi.boti.BOTIUtils;
import com.code.tama.triggerapi.helpers.world.BlockUtils;
import com.code.tama.tts.client.animations.consoles.ExteriorAnimationData;
import com.code.tama.tts.client.renderers.HalfBOTIRenderer;
import com.code.tama.tts.client.renderers.exteriors.AbstractJSONRenderer;
import com.code.tama.tts.server.blocks.tardis.ExteriorBlock;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

public class TardisExteriorRenderer<T extends ExteriorTile> implements BlockEntityRenderer<T> {

	// Door animation constants — tweak these to taste
	private static final float DOOR_MAX     = 5.625f; // counter range 0 → this
	private static final float DOOR_SPEED   = 0.10f;  // counter units per frame (~37 frames = ~1.8s)
	// (What the fuck was I on when I did that math... at 60fps 37 frames is roughly half a second)
	private static final float DOOR_MAX_DEG = 75f;    // max rotation in degrees when fully open

	public TardisExteriorRenderer(BlockEntityRendererProvider.Context context) {
	}

	public TardisExteriorRenderer() {
	}

	/**
	 * Smoothstep easing: ease-in AND ease-out.
	 * Input t is 0.0–1.0, output is 0.0–1.0.
	 * Accelerates off the latch, decelerates into the stop.
	 *
	 * TODO: Consider Swapping the body for:
	 *   (float) Math.sin(t * Math.PI / 2)
	 * for faster start, gradual stop. See how that looks. Maybe.
	 */
	private static float easing(float t) {
		return (float)((1.0 - Math.cos(t * Math.PI)) / 2.0);
	}

	@Override
	public void render(@NotNull T exteriorTile, float partialTicks, @NotNull PoseStack stack,
					   @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
		if (exteriorTile.getLevel() != null
				&& exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos()).getBlock().equals(Blocks.AIR))
			return;

		if (false && exteriorTile.isArtificial) {
			stack.pushPose();
			Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.SMOOTH_STONE_SLAB.defaultBlockState(),
					stack, bufferSource, combinedLight, combinedOverlay, ModelData.EMPTY, RenderType.solid());
			stack.popPose();
			return;
		}

		float transparency = exteriorTile.getTransparency();
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
		float leftAngle  = easing(data.FrameLeft  / DOOR_MAX) * DOOR_MAX_DEG;
		float rightAngle = easing(data.FrameRight / DOOR_MAX) * DOOR_MAX_DEG;

		stack.pushPose();
		float offs;
		if (exteriorTile.getLevel() != null)
			offs = -BlockUtils.getReverseHeightModifier(
					exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos().below()));
		else
			offs = 0;
		stack.translate(0.5, offs + 1.5, 0.5);

		if (exteriorTile.getLevel() != null) {
			stack.mulPose(exteriorTile.getLevel().getBlockState(exteriorTile.getBlockPos())
					.getValue(ExteriorBlock.FACING).getOpposite().getRotation());
			stack.mulPose(Axis.XN.rotationDegrees(90));
			stack.mulPose(Axis.ZN.rotationDegrees(180));
		}

		AbstractJSONRenderer ext = new AbstractJSONRenderer(exteriorTile.getModelIndex());
		JavaJSONModel parsed = JavaJSON.getParsedJavaJSON(ext).getModelInfo().getModel();

		parsed.getPart("LeftDoor").yRot  = (float) Math.toRadians( leftAngle);
		parsed.getPart("RightDoor").yRot = (float) Math.toRadians(-rightAngle);

		ModelPart boti        = parsed.getPart("BOTI").modelPart;
		ModelPart partialBOTI = parsed.getPart("PartialBOTI").modelPart;

		if (false) {
			HalfBOTIRenderer.render(exteriorTile.getLevel(), exteriorTile, stack, bufferSource, partialTicks,
					combinedLight, combinedOverlay);
		} else {
			stack.pushPose();
			stack.translate(0, 0, 0.5);

			// Flush Minecraft's pending geometry to main BEFORE hijacking the FBO binding.
			// Without this, geometry from other block entities sitting in bufferSource
			// gets drawn into the BOTI FBO instead of main.
			((MultiBufferSource.BufferSource) bufferSource).endBatch();

			exteriorTile.getFBOContainer().Render(stack,

					// STENCIL PASS — mark portal opening pixels, no color output
					(pose, botiSource) -> {
						pose.pushPose();
						pose.translate(0, 1.5, 0);
						boti.render(stack, botiSource.getBuffer(RenderType.solid()), 0xf000f0,
								OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
						if (true) // TODO: CONFIG FOR PARTIAL BOTI
							partialBOTI.render(stack, botiSource.getBuffer(RenderType.solid()), 0xf000f0,
									OverlayTexture.NO_OVERLAY, 0, 0, 0, 0);
						botiSource.endBatch();
						pose.popPose();
					},

					// FRAME PASS — unused, sky handled in scene pass
					(pose, buffer) -> {},

					// SCENE PASS — sky > BOTI blocks → door overlay (front-most)
					(pose, botiSource) -> {
						pose.pushPose();
						pose.translate(-0.5, 1.4, -0.62);
						pose.mulPose(Axis.XP.rotationDegrees(180));
						BOTIUtils.RenderScene(pose, exteriorTile);
						botiSource.endBatch();
						pose.popPose();

						pose.pushPose();
						pose.translate(0, 1.5, 0);
						RenderSystem.disableDepthTest();
						parsed.getPart("LeftDoor").render(stack,
								bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getTexture())),
								combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);
						parsed.getPart("RightDoor").render(stack,
								bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getTexture())),
								combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);

						((MultiBufferSource.BufferSource) bufferSource).endBatch();
						RenderSystem.enableDepthTest();
						pose.popPose();
					}
			);

			// Flush again after FBOHelper returns — anything queued inside the lambdas
			// via bufferSource lands on main while we know mainTarget is correctly bound.
			((MultiBufferSource.BufferSource) bufferSource).endBatch();

			stack.popPose();
		}

		stack.translate(0, 1.5, 0);
		parsed.getPart("baseRoot").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getTexture())),
				combinedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);

		parsed.getPart("baseRoot").render(stack,
				bufferSource.getBuffer(ext.getRenderType(exteriorTile.Model.getLightMap())),
				0xf000f0, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, transparency);

		((MultiBufferSource.BufferSource) bufferSource).endBatch();

		stack.popPose();
	}
}