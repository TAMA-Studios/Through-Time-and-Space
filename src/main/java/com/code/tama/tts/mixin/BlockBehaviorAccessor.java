/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.level.block.state.BlockBehaviour;

@Mixin(BlockBehaviour.class)
public interface BlockBehaviorAccessor {

	@Accessor
	BlockBehaviour.Properties getProperties();
}
