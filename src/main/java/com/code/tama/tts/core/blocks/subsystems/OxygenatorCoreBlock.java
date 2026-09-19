/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.blocks.subsystems;

import com.code.tama.tts.server.tardis.subsystems.OxygenatorCircuit;

import net.minecraft.world.level.block.SoundType;

public class OxygenatorCoreBlock extends AbstractSubsystemBlock {
	public OxygenatorCoreBlock(Properties properties) {
		super(properties.strength(1.5f).sound(SoundType.METAL), new OxygenatorCircuit());
	}
}
