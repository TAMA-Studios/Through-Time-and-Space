/* (C) TAMA Studios 2025 */
package com.code.tama.tts.config;

import lombok.Getter;

@Getter
public enum FlightType {
	BASIC("basic", "too easy!"), NORMAL("normal", "I can do this!"), ADVANCED("advanced", "Where's the manual?"),
	NIGHTMARE("nightmare", "12 blinking lights, 11 sparking fuses, 10 sketchy controls, 9 error codes, 8 blaring alarms, 7 OSHA violations, "
			+ "6 fires starting, 5 dimensional leakages, 4 fuses blown, 3 lights gone out, 2 rattling fixtures and a partridge and a pear tree");
	FlightType(String name, String altText) {
		this.name = name;
		this.altText = altText;
	}

	public final String name;
	public final String altText;
}
