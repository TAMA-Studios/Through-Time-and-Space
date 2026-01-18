/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.tardis.life;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.code.tama.tts.server.data.json.loaders.BehaviorLoader;
import com.code.tama.tts.server.data.json.loaders.MoodDPLoader;
import com.code.tama.tts.server.data.json.loaders.PersonalityDPLoader;

import com.code.tama.triggerapi.data.DatapackRegistry;
import com.code.tama.triggerapi.universal.UniversalCommon;

public class BehaviorHelper {

	/**
	 * @param personality
	 *            Base personality weights
	 * @param mood
	 *            Current mood modifier weights
	 * @param randomness
	 *            How chaotic the result should be (0 = deterministic, 1 = very
	 *            random)
	 */
	public static BehaviorLoader.TARDIBehavior pickBehavior(PersonalityDPLoader.TARDISPersonality personality,
			MoodDPLoader.TARDISMood mood, float randomness) {

		ThreadLocalRandom rng = ThreadLocalRandom.current();

		List<BehaviorLoader.TARDIBehavior> behaviors = (List<BehaviorLoader.TARDIBehavior>) DatapackRegistry
				.getLoader(UniversalCommon.modRL("tardis_behavior")).list.getList();

		if (behaviors.isEmpty()) {
			throw new IllegalStateException("No behaviors registered!");
		}

		// ------------------------------------------------------------
		// 1) Compute target weight (personality ↔ mood midpoint)
		// ------------------------------------------------------------
		float target = (personality.weight() + mood.base_weight()) * 0.5f;
		target = clamp01(target);

		// Small noise so identical weights don't always pick the same behavior
		float noise = rng.nextFloat(-0.02f, 0.02f);
		target = clamp01(target + noise);

		// ------------------------------------------------------------
		// 2) Convert distance to scores
		// ------------------------------------------------------------
		float[] scores = new float[behaviors.size()];
		float maxScore = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < behaviors.size(); i++) {
			BehaviorLoader.TARDIBehavior behavior = behaviors.get(i);

			float behaviorWeight = clamp01(behavior.weight());
			float distance = Math.abs(behaviorWeight - target);

			// Closer = higher score
			float score = -distance;

			scores[i] = score;
			maxScore = Math.max(maxScore, score);
		}

		// ------------------------------------------------------------
		// 3) Softmax normalize into probabilities
		// ------------------------------------------------------------
		// Map randomness -> temperature
		// 0 = almost deterministic
		// 1 = very chaotic
		float temperature = lerp(0.05f, 1.0f, clamp01(randomness));

		float sum = 0f;
		for (int i = 0; i < scores.length; i++) {
			// subtract maxScore for numerical stability
			float exp = (float) Math.exp((scores[i] - maxScore) / temperature);
			scores[i] = exp;
			sum += exp;
		}

		// ------------------------------------------------------------
		// 4) Weighted random selection
		// ------------------------------------------------------------
		float roll = rng.nextFloat() * sum;
		float cumulative = 0f;

		for (int i = 0; i < scores.length; i++) {
			cumulative += scores[i];
			if (roll <= cumulative) {
				return behaviors.get(i);
			}
		}

		// Fallback (floating point edge case)
		return behaviors.get(behaviors.size() - 1);
	}

	// ------------------------------------------------------------

	private static float clamp01(float v) {
		return Math.max(0f, Math.min(1f, v));
	}

	private static float lerp(float a, float b, float t) {
		return a + (b - a) * t;
	}
}
