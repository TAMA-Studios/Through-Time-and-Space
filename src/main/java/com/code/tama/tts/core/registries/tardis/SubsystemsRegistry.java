/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.registries.tardis;

import java.util.ArrayList;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;

public class SubsystemsRegistry {
	public static ArrayList<AbstractSubsystem> subsystems = new ArrayList<>();

	public static void addSubsystem(AbstractSubsystem system) {
		subsystems.add(system);
	}
}
