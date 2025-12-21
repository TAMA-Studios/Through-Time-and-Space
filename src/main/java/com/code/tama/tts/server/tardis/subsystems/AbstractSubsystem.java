/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import com.code.tama.tts.server.blocks.tardis.FragmentLinksBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSubsystem implements INBTSerializable<CompoundTag> {
	boolean Activated = false;
	BlockPos blockPos = BlockPos.ZERO;

	/**
	 * the Map uses a relative BlockPos, and the Default BlockState that make up
	 * this subsystem
	 */
	public abstract Map<BlockPos, BlockState> BlockMap();

	public boolean IsValid(Level level, BlockPos blockPos) {
		AtomicReference<Boolean> IsValid = new AtomicReference<>(true);
		for (Direction direction : Direction.values()) {
			if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN))
				continue;
			this.BlockMap().forEach((pos, state) -> {
				if (!IsValid.get())
					return;
				BlockState state1 = level.getBlockState(pos.offset(blockPos));
				if (!state1.getBlock().defaultBlockState().equals(state)
						&& !(state1.getBlock() instanceof FragmentLinksBlock && state.getBlock().equals(Blocks.AIR))) {
					IsValid.set(false);
					return;
				}
			});
		}
		return IsValid.get();
	}

	/** When the subsystem is activated * */
	public abstract void OnActivate(Level level, BlockPos blockPos);

	/** When the subsystem is de-activated * */
	public abstract void OnDeActivate(Level level, BlockPos blockPos);

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.Activated = nbt.getBoolean("active");
		this.blockPos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
	}

	public boolean isActivated(Level level) {
		return this.Activated && this.IsValid(level, this.blockPos);
	}

	public abstract String name();

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("active", this.Activated);
		if (this.blockPos != null)
			tag.put("pos", NbtUtils.writeBlockPos(this.blockPos));
		return tag;
	}
}
