/* (C) TAMA Studios 2025 */
package com.code.tama.tts.config;

import lombok.Getter;

@Getter
public enum FlightType {
	BASIC("basic", ""), NORMAL("normal", ""), ADVANCED("advanced", "");
	FlightType(String name, String altName) {
		this.name = name;
		this.altName = altName;
	}

	public final String name;
	public final String altName;
}
