/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;
import static com.code.tama.tts.server.tardis.controls.AbstractControl.Spark;

import java.util.List;

import com.code.tama.tts.core.entities.controls.ModularControl;
import com.code.tama.tts.core.items.gadgets.SonicItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;

public class ControlRenderer extends EntityRenderer<ModularControl> {

	/**
	 * TODO: Config! Or just, yk, keybind... Or F3+B...
	 */
	public static boolean SHOW_HITBOXES = true;

	// Slice colours — each slice gets a distinct hue so you can count them
	// visually.
	// RGBA, values 0-255.
	private static final int[][] SLICE_COLORS = {{255, 80, 80, 180}, // slice 0 — red
			{80, 200, 80, 180}, // slice 1 — green
			{80, 130, 255, 180}, // slice 2 — blue
			{255, 200, 50, 180}, // slice 3 — yellow (if ever 4 slices)
	};
	// Fallback colour for any slice beyond the array above
	private static final int[] FALLBACK_COLOR = {220, 80, 220, 180};

	public ControlRenderer(EntityRendererProvider.Context p_174008_) {
		super(p_174008_);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull ModularControl modularControl) {
		return new ResourceLocation("");
	}

	@Override
	public void render(@NotNull ModularControl entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack,
			@NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);

		// Name tag when holding sonic and looking at this control
		if (Minecraft.getInstance().hitResult instanceof EntityHitResult result && result.getEntity() == entity) {
			assert Minecraft.getInstance().player != null;
			if (Minecraft.getInstance().player.getMainHandItem().getItem() instanceof SonicItem
					|| Minecraft.getInstance().player.getOffhandItem().getItem() instanceof SonicItem) {
				this.renderNameTag(entity, entity.TranslationKey(), poseStack, buffer, packedLight);
			}
		}

		entity.GetControl().RenderFlightEvent(poseStack, buffer, entity);

		GetTARDISCapSupplier(entity.level()).ifPresent((cap) -> {
			if (cap.GetData().isSparking())
				if (entity.level().random.nextInt(100000) <= 5)
					Spark(entity.level(), entity.position());
		});

		// Hitbox slice overlay
		if (SHOW_HITBOXES) {
			renderSliceHitboxes(entity, poseStack, buffer);
		}
	}

	/**
	 * Renders each AABB slice from {@link ModularControl#getLocalHitboxSlices()}
	 */
	private void renderSliceHitboxes(ModularControl entity, PoseStack poseStack, MultiBufferSource buffer) {
		List<AABB> slices = entity.getLocalHitboxSlices();
		if (slices.isEmpty())
			return;

		// Minecraft's entity renderer has already pushed a translation to the entity's
		// render position. The local AABB slices are relative to entity origin (0,0,0),
		// so we can draw them directly without any extra translation.
		VertexConsumer lines = buffer.getBuffer(RenderType.lines());

		for (int i = 0; i < slices.size(); i++) {
			AABB slice = slices.get(i);
			int[] col = i < SLICE_COLORS.length ? SLICE_COLORS[i] : FALLBACK_COLOR;
			float r = col[0] / 255f;
			float g = col[1] / 255f;
			float b = col[2] / 255f;
			float a = col[3] / 255f;

			renderAABBLines(poseStack, lines, slice, r, g, b, a);
		}
	}

	/**
	 * Draws the 12 edges of an AABB as coloured lines. Uses
	 * {@link RenderType#lines()} which is depth-tested and works cleanly in the
	 * world.
	 */
	private static void renderAABBLines(PoseStack poseStack, VertexConsumer consumer, AABB box, float r, float g,
			float b, float a) {
		float x0 = (float) box.minX;
		float y0 = (float) box.minY;
		float z0 = (float) box.minZ;
		float x1 = (float) box.maxX;
		float y1 = (float) box.maxY;
		float z1 = (float) box.maxZ;

		PoseStack.Pose pose = poseStack.last();

		// Bottom face
		line(consumer, pose, x0, y0, z0, x1, y0, z0, r, g, b, a);
		line(consumer, pose, x1, y0, z0, x1, y0, z1, r, g, b, a);
		line(consumer, pose, x1, y0, z1, x0, y0, z1, r, g, b, a);
		line(consumer, pose, x0, y0, z1, x0, y0, z0, r, g, b, a);

		// Top face
		line(consumer, pose, x0, y1, z0, x1, y1, z0, r, g, b, a);
		line(consumer, pose, x1, y1, z0, x1, y1, z1, r, g, b, a);
		line(consumer, pose, x1, y1, z1, x0, y1, z1, r, g, b, a);
		line(consumer, pose, x0, y1, z1, x0, y1, z0, r, g, b, a);

		// Vertical edges
		line(consumer, pose, x0, y0, z0, x0, y1, z0, r, g, b, a);
		line(consumer, pose, x1, y0, z0, x1, y1, z0, r, g, b, a);
		line(consumer, pose, x1, y0, z1, x1, y1, z1, r, g, b, a);
		line(consumer, pose, x0, y0, z1, x0, y1, z1, r, g, b, a);
	}

	/**
	 * Emits a single line segment. RenderType.lines() uses LINES topology so each
	 * pair of vertices forms one segment — normals are required by the format.
	 */
	private static void line(VertexConsumer consumer, PoseStack.Pose pose, float x0, float y0, float z0, float x1,
			float y1, float z1, float r, float g, float b, float a) {
		// Direction vector (used as normal for the lines render type)
		float dx = x1 - x0;
		float dy = y1 - y0;
		float dz = z1 - z0;
		float len = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
		if (len == 0)
			return;
		dx /= len;
		dy /= len;
		dz /= len;

		consumer.vertex(pose.pose(), x0, y0, z0).color(r, g, b, a).normal(pose.normal(), dx, dy, dz).endVertex();
		consumer.vertex(pose.pose(), x1, y1, z1).color(r, g, b, a).normal(pose.normal(), dx, dy, dz).endVertex();
	}

	@Override
	public boolean shouldRender(@NotNull ModularControl entity, @NotNull Frustum camera, double camX, double camY,
			double camZ) {
		return super.shouldRender(entity, camera, camX, camY, camZ);
	}
}