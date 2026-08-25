/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Base for an animated block that supports all three AnimatedBlockConfig modes
 * at once, the active Mode decides which mechanism actually does anything; the
 * other two are inert. That's the whole point of the config: swap MODE and
 * nothing else in your block class needs to change. <br />
 * getRenderShape() is always INVISIBLE regardless of mode: LEVEL_EVENT and
 * MIXIN draw nothing through the chunk mesh at all, and BLOCK_ENTITY draws
 * through its own renderer instead of the static model too.
 */
@SuppressWarnings("deprecation")
public abstract class AnimatedGeoBlockBase extends Block implements IGeoAnimatedBlock, EntityBlock {

	protected AnimatedGeoBlockBase(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.INVISIBLE;
	}

	// --- LEVEL_EVENT / MIXIN backends: populate the shared registry ---

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
			@NotNull BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);
		if (level.isClientSide && AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.add(pos, this);
		}
	}

	@Override
	public void onRemove(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState newState,
			boolean isMoving) {
		if (level.isClientSide && !state.is(newState.getBlock())
				&& AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY) {
			AnimatedBlockRegistry.remove(pos);
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	// --- BLOCK_ENTITY backend: only actually creates one in that mode ---

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.BLOCK_ENTITY)
			return null;
		return new AnimatedGeoBlockEntity(getBlockEntityType(), pos, state);
	}

	/** Return your registered BlockEntityType<AnimatedGeoBlockEntity> here. */
	protected abstract net.minecraft.world.level.block.entity.BlockEntityType<AnimatedGeoBlockEntity> getBlockEntityType();
}
