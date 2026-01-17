/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.tardis.life;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.code.tama.tts.server.data.json.DatapackRegistry;
import com.code.tama.tts.server.data.json.loaders.BehaviorLoader;
import com.code.tama.tts.server.data.json.loaders.MoodDPLoader;
import com.code.tama.tts.server.data.json.loaders.PersonalityDPLoader;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class BehaviorHelper {

	/**
	 * @param personality
	 *            Base personality weights
	 * @param mood
	 *            Current mood modifier weights
	 * @param randomness
	 *            How chaotic the result should be (0 = deterministic)
	 */
	public static BehaviorLoader.TARDIBehavior pickBehavior(PersonalityDPLoader.TARDISPersonality personality,
			MoodDPLoader.TARDISMood mood, float randomness) {
		ThreadLocalRandom rng = ThreadLocalRandom.current();

		List<BehaviorLoader.TARDIBehavior> behaviors = (List<BehaviorLoader.TARDIBehavior>) DatapackRegistry
				.getLoader(UniversalCommon.modRL("tardis_behavior")).list.getList();

		if (behaviors.isEmpty()) {
			throw new IllegalStateException("No behaviors registered!");
		}

		float currentMood = clamp01(personality.weight() + mood.base_weight());

		float[] scores = new float[behaviors.size()];
		float maxScore = Float.NEGATIVE_INFINITY;

		// ---- 1) Compute raw scores (higher = better) ----
		for (int i = 0; i < behaviors.size(); i++) {
			BehaviorLoader.TARDIBehavior behavior = behaviors.get(i);

			float behaviorWeight = clamp01(behavior.weight());
			float distance = Math.abs(behaviorWeight - currentMood);

			// Convert distance into a score (closer = higher)
			float score = -distance;

			scores[i] = score;
			maxScore = Math.max(maxScore, score);
		}

		// ---- 2) Softmax normalize into probabilities ----
		float temperature = Math.max(0.0001f, randomness); // prevent divide-by-zero
		float sum = 0f;

		for (int i = 0; i < scores.length; i++) {
			// subtract maxScore for numeric stability
			scores[i] = (float) Math.exp((scores[i] - maxScore) / temperature);
			sum += scores[i];
		}

		// ---- 3) Weighted random selection ----
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

	private static float clamp01(float v) {
		return Math.max(0f, Math.min(1f, v));
	}
}
