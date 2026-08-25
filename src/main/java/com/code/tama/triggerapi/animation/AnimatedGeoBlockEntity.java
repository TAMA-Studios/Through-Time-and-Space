/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Deliberately does almost nothing: no NBT, no tick(). It exists purely so a
 * BlockEntityRenderer can be attached (that's the only reason blocks in the
 * world need one for per-frame animation at all). Your block's getTicker(...)
 * should return null so this never enters the tick list. <br />
 * The AnimationPlayer lives here because per-instance state (which animation,
 * when it started) has to live somewhere, this is the smallest object that can
 * hold it.
 */
public class AnimatedGeoBlockEntity extends BlockEntity {

	public final AnimationPlayer player = new AnimationPlayer();

	public AnimatedGeoBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	// Intentionally no saveAdditional/loadAdditional overrides, nothing persists.
	// If you need the current animation to survive a chunk reload, override both
	// and store just the animation name + a game-time start tick, nothing more.
}
