/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.NativeLoader;

/**
 * Math utilities.
 */
public class MathUtils {

	static {
		NativeLoader.load("tts_native");
	}

	// -- Native (Rust) --------------------------------------------------------

	public static native int roundToMultipleInt(int num, int multiple);
	public static native int roundToMultipleFloat(float num, int multiple);
	public static native int roundToMultipleDouble(double num, int multiple);
	public static native int roundTo48(int num);
	public static native int reverseRoundTo48(int num);
	public static native int reverseRound(double value);
	public static native double angleBetween(double x1, double y1, double z1, double x2, double y2, double z2);
	public static native float clampFloat(float value, float min, float max);
	public static native long clampLong(long value, long min, long max);
	public static native double clampDouble(double value, double min, double max);
	public static native float getSlopedRotation(float slope, float desiredRot);
	public static native float lerp(float start, float end, float t);
	public static native int packLight(int blockLight, int skyLight);

	/**
	 * blockType encoding: 0=full block, 1=bottom slab, 2=snow (pass layers 1-8),
	 * 3=carpet, 4=air
	 */
	public static native float heightModifier(int blockType, int snowLayers);
	public static native float differenceInHeight(int fromType, int fromLayers, int toType, int toLayers);

	// -- Convenience overloads (delegate to natives) --------------------------

	/** @see #roundToMultipleInt */
	public static int RoundToMultiple(int num, int multiple) {
		return roundToMultipleInt(num, multiple);
	}
	/** @see #roundToMultipleFloat */
	public static int RoundToMultiple(float num, int multiple) {
		return roundToMultipleFloat(num, multiple);
	}
	/** @see #roundToMultipleDouble */
	public static int RoundToMultiple(double num, int multiple) {
		return roundToMultipleDouble(num, multiple);
	}
	/** @see #roundTo48 */
	public static int RoundTo48(int num) {
		return roundTo48(num);
	}
	/** @see #reverseRoundTo48 */
	public static int ReverseRoundTo48(int num) {
		return reverseRoundTo48(num);
	}
	/** @see #reverseRound */
	public static int reverseRound(double value, boolean unused) {
		return reverseRound(value);
	} // kept for compat
	/** @see #clampFloat */
	public static float clamp(float value, float min, float max) {
		return clampFloat(value, min, max);
	}
	/** @see #clampLong */
	public static long clamp(long value, long min, long max) {
		return clampLong(value, min, max);
	}
	/** @see #clampDouble */
	public static double clamp(double value, double min, double max) {
		return clampDouble(value, min, max);
	}

	// -- MC-dependent (kept in Java — needs Vec3 object) ----------------------

	/**
	 * Angle (degrees) between two Vec3 vectors. Delegates to native using raw
	 * components to avoid JObject overhead.
	 */
	public static double angleBetween(Vec3 vec1, Vec3 vec2) {
		return angleBetween(vec1.x, vec1.y, vec1.z, vec2.x, vec2.y, vec2.z);
	}
}