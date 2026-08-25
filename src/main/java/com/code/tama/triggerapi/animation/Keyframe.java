/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.List;

/**
 * @param time
 *            seconds
 */
public record Keyframe(float time, float x, float y, float z) {

	/**
	 * Linear-interpolate x/y/z between two keyframes at the given time (seconds).
	 * Extend here for easing curves.
	 */
	public static float[] sample(List<Keyframe> track, float time, float[] fallback) {
		if (track == null || track.isEmpty())
			return fallback;
		if (time <= track.get(0).time) {
			Keyframe k = track.get(0);
			return new float[]{k.x, k.y, k.z};
		}
		Keyframe last = track.get(track.size() - 1);
		if (time >= last.time)
			return new float[]{last.x, last.y, last.z};

		for (int i = 0; i < track.size() - 1; i++) {
			Keyframe a = track.get(i);
			Keyframe b = track.get(i + 1);
			if (time >= a.time && time <= b.time) {
				float span = b.time - a.time;
				float t = span <= 0f ? 0f : (time - a.time) / span;
				return new float[]{lerp(a.x, b.x, t), lerp(a.y, b.y, t), lerp(a.z, b.z, t)};
			}
		}
		return fallback;
	}

	private static float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}
}
