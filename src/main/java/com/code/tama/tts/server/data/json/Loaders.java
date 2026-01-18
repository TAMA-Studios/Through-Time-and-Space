/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.loaders.BehaviorLoader;
import com.code.tama.tts.server.data.json.loaders.MoodDPLoader;
import com.code.tama.tts.server.data.json.loaders.PersonalityDPLoader;
import lombok.Getter;

import com.code.tama.triggerapi.data.DatapackRegistry;

@Getter
public class Loaders {
	private static final BehaviorLoader behaviorLoader = new BehaviorLoader();
	private static final MoodDPLoader moodLoader = new MoodDPLoader();
	private static final PersonalityDPLoader personalityLoader = new PersonalityDPLoader();

	public static void registerAll() {
		DatapackRegistry.addLoader(behaviorLoader);
		DatapackRegistry.addLoader(moodLoader);
		DatapackRegistry.addLoader(personalityLoader);
	}
}