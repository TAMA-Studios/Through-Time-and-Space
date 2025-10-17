/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries.tardis;

import java.util.ArrayList;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;

@SuppressWarnings("unused")
public class SubsystemsRegistry {
	public static final AbstractSubsystem demat;

	public static final AbstractSubsystem dimensionalCore;
	public static final AbstractSubsystem dynamoController;
	public static ArrayList<AbstractSubsystem> subsystems = new ArrayList<>();

	static {
		demat = addSubsystem(new DematerializationCircuit());
		dynamoController = addSubsystem(new DynamorphicController());
		dimensionalCore = addSubsystem(new NetherReactorCoreSubsystem());
	}

	public static AbstractSubsystem addSubsystem(AbstractSubsystem system) {
		subsystems.add(system);
		return system;
	}
}
