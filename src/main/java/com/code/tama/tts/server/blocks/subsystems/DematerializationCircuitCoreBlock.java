/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import com.code.tama.tts.server.tardis.subsystems.DematerializationCircuit;

import net.minecraft.world.level.block.SoundType;

public class DematerializationCircuitCoreBlock extends AbstractSubsystemBlock {
	public DematerializationCircuitCoreBlock(Properties properties) {
		super(properties.strength(1.5f).sound(SoundType.METAL), new DematerializationCircuit());
	}
}
