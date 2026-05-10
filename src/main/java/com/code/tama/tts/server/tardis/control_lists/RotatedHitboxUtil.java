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
	 * in LOCAL space (centered on origin). call .move(entity.position()) before
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
	 *            number of AABBs. 3 is fine for small controls, 4 for anything
	 *            larger
	 */
	public static List<AABB> makeSlices(double hw, double hh, double hd, float yawDeg, int slices) {
		return makeSlices(hw, -hh, hh, hd, yawDeg, slices);
	}

	public static AABB rotatedTightAABB(double hw, double hh, double hd, float yawDeg) {
		double rad = Math.toRadians(yawDeg);
		double cos = Math.abs(Math.cos(rad));
		double sin = Math.abs(Math.sin(rad));

		// Half-extents of the axis-aligned envelope of the rotated box
		double ex = hw * cos + hd * sin;
		double ez = hw * sin + hd * cos;

		return new AABB(-ex, 0, -ez, ex, hh * 2, ez);
	}

	public static List<AABB> makeSlices(double hw, double yMin, double yMax, double hd, float yawDeg, int slices) {
		List<AABB> result = new ArrayList<>(slices);

		double yawRad = Math.toRadians(yawDeg);
		double cos = Math.abs(Math.cos(yawRad));
		double sin = Math.abs(Math.sin(yawRad));

		double fullWidth = hw * 2;

		for (int i = 0; i < slices; i++) {
			double t = (i + 0.5) / slices;
			double along = (t - 0.5) * fullWidth;

			double cx = along * cos;
			double cz = along * sin;

			double sliceHW = ((fullWidth / slices) * cos + hd * 2 * sin) / 2.0;
			double sliceHD = ((fullWidth / slices) * sin + hd * 2 * cos) / 2.0;

			result.add(new AABB(cx - sliceHW, yMin, cz - sliceHD, cx + sliceHW, yMax, cz + sliceHD));
		}

		return result;
	}
}