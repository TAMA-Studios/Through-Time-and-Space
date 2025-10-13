/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.registries;

import com.code.tama.tts.server.tardis.subsystems.AbstractSubsystem;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import java.util.ArrayList;

public class SubsystemsRegistry {
  public static ArrayList<AbstractSubsystem> subsystems = new ArrayList<>();

  public static final AbstractSubsystem demat;
  public static final AbstractSubsystem dynamoController;
  public static final AbstractSubsystem dimensionalCore;

  public static AbstractSubsystem addSubsystem(AbstractSubsystem system) {
    subsystems.add(system);
    return system;
  }

  static {
    demat = addSubsystem(new DematerializationCircuit());
    dynamoController = addSubsystem(new DynamorphicController());
    dimensionalCore = addSubsystem(new NetherReactorCoreSubsystem());
  }
}
