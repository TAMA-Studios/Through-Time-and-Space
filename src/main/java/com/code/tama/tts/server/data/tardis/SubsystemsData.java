/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.codecs.SubsystemsCodecs;
import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicController;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicGeneratorStack;
import com.code.tama.tts.server.tardis.subsystems.NetherReactorCoreSubsystem;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubsystemsData {
	public static Codec<SubsystemsData> CODEC = RecordCodecBuilder
			.create(instance -> instance
					.group(SubsystemsCodecs.DEMAT_CIRCUIT.fieldOf("demat_circuit")
							.forGetter(SubsystemsData::getDematerializationCircuit),
							SubsystemsCodecs.DIMENSIONAL_CORE.fieldOf("dimensional_core")
									.forGetter(SubsystemsData::getNetherReactorCoreSubsystem),
							SubsystemsCodecs.DYNAMORPHIC_CONTROLLER.fieldOf("dynamorphic_controller")
									.forGetter(SubsystemsData::getDynamorphicController),
							SubsystemsCodecs.DYNAMORPHIC_STACK_LIST.fieldOf("dynamorphic_stacks")
									.forGetter(SubsystemsData::getDynamorphicGeneratorStacks))
					.apply(instance, SubsystemsData::new));

	public ITARDISLevel tardis;
	public DematerializationCircuit DematerializationCircuit = new DematerializationCircuit();
	public DynamorphicController DynamorphicController = new DynamorphicController();
	public ArrayList<DynamorphicGeneratorStack> DynamorphicGeneratorStacks = new ArrayList<DynamorphicGeneratorStack>();
	public NetherReactorCoreSubsystem NetherReactorCoreSubsystem = new NetherReactorCoreSubsystem();

	public SubsystemsData(DematerializationCircuit dematerializationCircuit,
			NetherReactorCoreSubsystem netherReactorCoreSubsystem, DynamorphicController dynamorphicController,
			List<DynamorphicGeneratorStack> dynamoStacks) {
		this.DematerializationCircuit = dematerializationCircuit;
		this.NetherReactorCoreSubsystem = netherReactorCoreSubsystem;
		this.DynamorphicController = dynamorphicController;
		this.DynamorphicGeneratorStacks = new ArrayList<>(dynamoStacks);
	}

	public int activeGeneratorStacks() {
		int a = 0;
		for (DynamorphicGeneratorStack dynamorphicGeneratorStack : DynamorphicGeneratorStacks) {
			if (dynamorphicGeneratorStack.isActivated(tardis.GetLevel()))
				a++;
		}
		return a;
	}
}
