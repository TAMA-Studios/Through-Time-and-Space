/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import java.util.ArrayList;

import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.tardis.flightsoundschemes.SmithSoundScheme;
import com.mojang.serialization.Codec;

@SuppressWarnings("unused")
public class FlightSoundSchemesRegistry {
	public static final Codec<AbstractSoundScheme> CODEC = Codec.STRING.xmap(FlightSoundSchemesRegistry::GetByName,
			AbstractSoundScheme::GetName);

	public static ArrayList<AbstractSoundScheme> FLIGHT_SOUND_SCHEMES = new ArrayList<>();

	public static AbstractSoundScheme SMITH = AddSoundScheme(new SmithSoundScheme());

	public static AbstractSoundScheme AddSoundScheme(AbstractSoundScheme structure) {
		FLIGHT_SOUND_SCHEMES.add(structure);
		return structure;
	}

	public static AbstractSoundScheme CycleProt(AbstractSoundScheme structure) {
		for (int i = 0; i < FLIGHT_SOUND_SCHEMES.size() - 1; i++) {
			if (GetSoundScheme(i).equals(structure))
				return GetSoundScheme(i + 1);
		}
		return GetSoundScheme(0);
	}

	public static AbstractSoundScheme GetByName(String name) {
		for (AbstractSoundScheme protocol : FLIGHT_SOUND_SCHEMES) {
			if (protocol.GetName().equals(name))
				return protocol;
		}
		return FLIGHT_SOUND_SCHEMES.get(0);
	}

	public static AbstractSoundScheme GetSoundScheme(int ID) {
		return FLIGHT_SOUND_SCHEMES.get(ID);
	}
}
