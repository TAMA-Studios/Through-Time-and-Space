/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.codecs;

import java.util.ArrayList;
import java.util.List;

import com.code.tama.tts.server.tardis.subsystems.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;

public class SubsystemsCodecs {
	public static Codec<DematerializationCircuit> DEMAT_CIRCUIT = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
					Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
			.apply(instance, DematerializationCircuit::new));

	public static Codec<NetherReactorCoreSubsystem> DIMENSIONAL_CORE = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
					Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
			.apply(instance, NetherReactorCoreSubsystem::new));

	public static Codec<DynamorphicController> DYNAMORPHIC_CONTROLLER = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
					Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
			.apply(instance, DynamorphicController::new));

	public static Codec<DynamorphicGeneratorStack> DYNAMORPHIC_STACK = RecordCodecBuilder.create(instance -> instance
			.group(BlockPos.CODEC.fieldOf("pos").forGetter(AbstractSubsystem::getBlockPos),
					Codec.BOOL.fieldOf("active").forGetter(AbstractSubsystem::isActivated))
			.apply(instance, DynamorphicGeneratorStack::new));

	public static Codec<List<DynamorphicGeneratorStack>> DYNAMORPHIC_STACK_LIST = RecordCodecBuilder
			.create(instance -> instance.group(DYNAMORPHIC_STACK.listOf().fieldOf("list").forGetter((l) -> l))
					.apply(instance, ArrayList<DynamorphicGeneratorStack>::new));
}
