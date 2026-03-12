/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.data.TARDISNavigationalData;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

@Mixin(targets = "net.minecraft.client.gui.MapRenderer$MapInstance")
public abstract class MixinMapRenderer {

	/**
	 * Inject at the tail of the render method so our dots draw on top of everything
	 * (map texture, existing decorations, etc.)
	 * <p>
	 * MapInstance.draw signature (mojmaps 1.20.1): draw(PoseStack,
	 * MultiBufferSource, boolean, int)
	 */
	@Inject(method = "draw(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ZI)V", at = @At("TAIL"))
	private void tts$drawTARDISMarkers(PoseStack poseStack, MultiBufferSource bufferSource, boolean active,
			int packedLight, CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();
		Level level = mc.level;
		if (level == null)
			return;

		ITARDISLevel tardisCap = TARDISLevelCapability.GetTARDISCap(level);
		if (tardisCap == null)
			return;

		TARDISNavigationalData nav = tardisCap.GetNavigationalData();
		if (nav == null)
			return;

		// Grab the MapItemSavedData from the MapInstance via the accessor
		MapItemSavedData mapData = ((MapInstanceAccessor) (Object) this).tts$getData();
		if (mapData == null)
			return;

		int mapCenterX = mapData.centerX;
		int mapCenterZ = mapData.centerZ;
		int scale = mapData.scale; // 0-4

		Matrix4f matrix = poseStack.last().pose();
		// Use the debug-line render type — it ignores depth and draws as solid quads.
		// gui_overlay is also fine; we use POSITION_COLOR explicitly.
		VertexConsumer buf = bufferSource.getBuffer(RenderType.debugFilledBox());

		// Current location — blue (R=0.27, G=0.53, B=1.0)
		SpaceTimeCoordinate loc = nav.getLocation();

		// After getting mapData, get the map's dimension
		ResourceKey<Level> mapDimension = mapData.dimension;

		if (loc != null && mapDimension.equals(loc.getLevel())) {
			float px = worldToMapF(loc.GetBlockPos().getX(), mapCenterX, scale);
			float pz = worldToMapF(loc.GetBlockPos().getZ(), mapCenterZ, scale);
			if (onMap(px, pz)) {
				drawSquare(matrix, buf, px, pz, 0.27f, 0.53f, 1.00f, 1.0f, packedLight);
			}
		}

		// Destination — pink (R=1.0, G=0.41, B=0.71)
		SpaceTimeCoordinate dest = nav.getDestination();

		if (dest != null && mapDimension.equals(dest.getLevel())) {
			float px = worldToMapF(dest.GetBlockPos().getX(), mapCenterX, scale);
			float pz = worldToMapF(dest.GetBlockPos().getZ(), mapCenterZ, scale);
			if (onMap(px, pz)) {
				drawSquare(matrix, buf, px, pz, 1.00f, 0.41f, 0.71f, 1.0f, packedLight);
			}
		}
	}

	// -----------------------------------------------------------------------
	// Coordinate helpers
	// -----------------------------------------------------------------------

	/**
	 * Converts a world coord to a map-local float in [0, 128]. The map quad
	 * rendered by vanilla spans exactly 0-128 in local space.
	 */
	private static float worldToMapF(int worldCoord, int mapCenter, int scale) {
		return 64.0f + (float) (worldCoord - mapCenter) / (float) (1 << scale);
	}

	private static boolean onMap(float px, float pz) {
		return px >= 0 && px < 128 && pz >= 0 && pz < 128;
	}

	// -----------------------------------------------------------------------
	// Drawing
	// -----------------------------------------------------------------------

	/**
	 * Draws a 3x3 pixel solid square centered on (cx, cz) in map-local space.
	 * <p>
	 * Map-local space: the vanilla map quad is 128x128 units where X = east, Z =
	 * south. The PoseStack at this point already has the map's model transform
	 * applied, so we just need to emit quads in that local space.
	 */
	private static void drawSquare(Matrix4f m, VertexConsumer buf, float cx, float cz, float r, float g, float b,
			float a, int packedLight) {
		float x0 = cx - 1.5f, x1 = cx + 1.5f;
		float z0 = cz - 1.5f, z1 = cz + 1.5f;
		float y = -0.001f;

		buf.vertex(m, x0, z0, y).color(r, g, b, a).endVertex();
		buf.vertex(m, x1, z0, y).color(r, g, b, a).endVertex();
		buf.vertex(m, x1, z1, y).color(r, g, b, a).endVertex();
		buf.vertex(m, x0, z1, y).color(r, g, b, a).endVertex();
	}
}