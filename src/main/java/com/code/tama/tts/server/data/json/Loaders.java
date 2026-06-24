/* (C) TAMA Studios 2026 */
package com.code.tama.tts.server.data.json;

import com.code.tama.tts.server.data.json.loaders.*;
import lombok.Getter;

import com.code.tama.triggerapi.data.DatapackRegistry;

@Getter
public class Loaders {
	public static void registerAll() {
		DatapackRegistry.addLoader(new PlanetLoader());
		DatapackRegistry.addLoader(new BehaviorLoader());
		DatapackRegistry.addLoader(new MoodDPLoader());
		DatapackRegistry.addLoader(new PersonalityDPLoader());
		DatapackRegistry.addLoader(new InteriorHumDPLoader());
	}
}