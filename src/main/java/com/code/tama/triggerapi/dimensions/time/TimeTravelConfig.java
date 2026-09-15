/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

/**
 * Tunables for the temporal system.
 * <p>
 * {@link #EROSION_BIAS_PER_STEP} is the one worth playing with in-game before
 * committing: too subtle and nobody notices the future looks different at all;
 * too strong and ground-relative edits start visibly sliding up and down hills
 * that moved underneath them. Start conservative and push it until it reads.
 */
public final class TimeTravelConfig {

	private TimeTravelConfig() {
	}

	/**
	 * Added to the base level's {@code erosion()} density function, per decay step.
	 * Negative reads as "more eroded" on the vanilla erosion scale.
	 */
	public static double EROSION_BIAS_PER_STEP = -0.15;

	/**
	 * Noise value above which a surface column weathers. Higher = rarer, more
	 * patchy.
	 */
	public static double WEATHER_THRESHOLD = 0.25;

	/** How many blocks down from the surface the weathering pass reaches. */
	public static int WEATHER_DEPTH = 3;

	/** Bias for a given decay depth (1 = present, 2 = future). */
	public static double erosionBias(int steps) {
		return EROSION_BIAS_PER_STEP * steps;
	}
}