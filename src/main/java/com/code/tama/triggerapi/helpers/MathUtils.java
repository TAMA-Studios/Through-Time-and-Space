/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import net.minecraft.world.phys.Vec3;

public class MathUtils {
	public static int ReverseRoundTo48(int num) {
		return MathUtils.reverseRound((float) num / 48) * 48;
	}

	public static int RoundTo48(int num) {
		return RoundToMultiple(num, 48);
	}

	/**
	 * @return The number rounded to the nearest multiple
	 */
	public static int RoundToMultiple(int num, int multiple) {
		return Math.round((float) num / multiple) * multiple;
	}

	/**
	 * @return The number rounded to the nearest multiple
	 */
	public static int RoundToMultiple(float num, int multiple) {
		return Math.round((float) num / multiple) * multiple;
	}

	/**
	 * @return The number rounded to the nearest multiple
	 */
	public static int RoundToMultiple(double num, int multiple) {
		return Math.round((float) num / multiple) * multiple;
	}

	public static double angleBetween(Vec3 vec1, Vec3 vec2) {
		double dot = vec1.dot(vec2);
		double mag = vec1.length() * vec2.length();
		return Math.toDegrees(Math.acos(dot / mag));
	}

	/**
	 * @param value
	 *            The value to clamp
	 * @param min
	 *            the minimum allowed amount (inclusive)
	 * @param max
	 *            the maximum allowed amount (inclusive)
	 * @return a value that is no less than min, and also no greater than max
	 */
	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Adjusts the slope based on the desired rotation around the Y-axis.
	 *
	 * @param slope
	 *            The slope (pitch).
	 * @param desiredRot
	 *            The desired rotation around the Y-axis in degrees.
	 * @return The adjusted slope after applying the rotation.
	 */
	public static float getSlopedRotation(float slope, float desiredRot) {
		// Normalize desiredRot to [0, 360)
		desiredRot = ((desiredRot % 360) + 360) % 360;

		// If no rotation, keep slope
		if (desiredRot == 0f) {
			return slope;
		}

		// For 180° around Y, invert the slope
		if (desiredRot == 180f) {
			return -slope;
		}

		// Otherwise interpolate based on rotation
		// After 90 yaw, slope pitch becomes 0
		// After 270 yaw, slope pitch becomes 0
		double radians = Math.toRadians(desiredRot);

		return (float) (slope * Math.cos(radians));
	}

	public static float lerp(float start, float end, float t) {
		return start + t * (end - start);
	}

	public static int reverseRound(double value) {
		int intPart = (int) value;
		double decimalPart = Math.abs(value - intPart);

		if (decimalPart >= 0.5) {
			// Round toward zero
			return intPart;
		} else {
			// Round away from zero
			return value >= 0 ? intPart + 1 : intPart - 1;
		}
	}
}
