/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin;

import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Block.class)
public interface BlockAccessor {
	@Accessor("OCCLUSION_CACHE")
    static ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> getOcclusionCache() {
		throw new AssertionError();
	}
}
