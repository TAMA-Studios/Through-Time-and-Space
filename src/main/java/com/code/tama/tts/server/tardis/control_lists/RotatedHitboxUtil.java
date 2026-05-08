/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.control_lists;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.phys.AABB;

public final class RotatedHitboxUtil {

	private RotatedHitboxUtil() {
	}

	/**
	 * Approximates a yaw-rotated box as a list of axis-aligned AABBs. All boxes are
	 * in LOCAL space (centered on origin) — call .move(entity.position()) before
	 * use.
	 *
	 * @param hw
	 *            half-width (X extent)
	 * @param hh
	 *            half-height (Y extent)
	 * @param hd
	 *            half-depth (Z extent)
	 * @param yawDeg
	 *            rotation around Y axis in degrees
	 * @param slices
	 *            number of AABBs — 3 is fine for small controls, 4 for anything
	 *            larger
	 */
	public static List<AABB> makeSlices(double hw, double hh, double hd, float yawDeg, int slices) {
		List<AABB> result = new ArrayList<>(slices);

		double yawRad = Math.toRadians(yawDeg);
		double cos = Math.abs(Math.cos(yawRad));
		double sin = Math.abs(Math.sin(yawRad));

		// Full width along the rotated long axis
		double fullWidth = hw * 2;

		for (int i = 0; i < slices; i++) {
			// Center of this slice along the rotated axis (-hw..+hw)
			double t = (i + 0.5) / slices;
			double along = (t - 0.5) * fullWidth;

			// Slice center in world-ish XZ
			double cx = along * cos;
			double cz = along * sin;

			// Slice extents, shrink X/Z so the union doesn't bloat
			double sliceHW = ((fullWidth / slices) * cos + hd * 2 * sin) / 2.0;
			double sliceHD = ((fullWidth / slices) * sin + hd * 2 * cos) / 2.0;

			result.add(new AABB(cx - sliceHW, -hh, cz - sliceHD, cx + sliceHW, hh, cz + sliceHD));
		}

		return result;
	}
}