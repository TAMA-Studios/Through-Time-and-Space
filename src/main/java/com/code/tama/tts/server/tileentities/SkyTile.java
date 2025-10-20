/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import java.util.Arrays;

import com.code.tama.tts.server.registries.forge.TTSTileEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SkyTile extends BlockEntity {

	private final Boolean[] shouldRender = new Boolean[6];

	private SkyType skyType = SkyType.Overworld;

	public SkyTile(BlockPos blockPos, BlockState blockState) {
		super(TTSTileEntities.SKY_TILE.get(), blockPos, blockState);
	}

	public SkyTile(SkyType skyType, BlockPos pos, BlockState state) {
		this(pos, state);
		this.skyType = skyType;
	}

	@Override
	protected void saveAdditional(CompoundTag compoundTag) {
		compoundTag.putString("skyType", this.skyType.name());
	}

	public SkyType getSkyType() {
		return skyType;
	}

	@Override
	public void load(CompoundTag compoundTag) {
		if (!compoundTag.contains("skyType")) {
			return;
		}
		this.skyType = SkyType.valueOf(compoundTag.getString("skyType"));
	}

	public void neighborChanged() {
		Arrays.fill(shouldRender, null);
	}

	public boolean shouldRenderFace(Direction direction) {
		int index = direction.ordinal();

		if (shouldRender[index] == null) {
			shouldRender[index] = level == null || Block.shouldRenderFace(getBlockState(), level, getBlockPos(),
					direction, getBlockPos().relative(direction));
		}

		return shouldRender[index];
	}

	public enum SkyType {
		Overworld, Void,
	}
}
