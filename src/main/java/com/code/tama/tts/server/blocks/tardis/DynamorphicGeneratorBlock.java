/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.tardis;

import com.code.tama.tts.server.blocks.subsystems.AbstractSubsystemBlock;
import com.code.tama.tts.server.tardis.subsystems.DynamorphicGeneratorStack;

public class DynamorphicGeneratorBlock extends AbstractSubsystemBlock {
	public DynamorphicGeneratorBlock(Properties p_49795_) {
		super(p_49795_, new DynamorphicGeneratorStack());
	}
}
