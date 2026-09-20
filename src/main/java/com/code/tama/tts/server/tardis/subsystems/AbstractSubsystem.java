/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.Map;

import com.code.tama.tts.core.registries.forge.TTSBlocks;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.lwjgl.system.MemoryUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractSubsystem implements INBTSerializable<CompoundTag>, ImASubsystem {
	boolean Activated = false;
	BlockPos blockPos = BlockPos.ZERO;

	/**
	 * the Map uses a relative BlockPos, and the Default BlockState that make up
	 * this subsystem
	 */
	public abstract Map<BlockPos, BlockState> BlockMap();

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.Activated = nbt.getBoolean("active");
		this.blockPos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
	}

	public boolean IsValid(Level level, BlockPos pos) {
		long nopeAddr = MemoryUtil.nmemAlloc(1); // Lambda requires "Final or effectively final" and I aint making an
													// atomic bool
		MemoryUtil.memPutByte(nopeAddr, (byte) 0);

		this.BlockMap().forEach((pos1, state) -> {
			BlockPos worldPos = this.getBlockPos().offset(pos1);
			BlockState one = level.getBlockState(worldPos).getBlock().defaultBlockState();
			BlockState two = state.getBlock().defaultBlockState();
			if (!one.equals(two) && !one.equals(TTSBlocks.FRAGMENT_LINKS.getDefaultState())) {
				MemoryUtil.memPutByte(nopeAddr, (byte) 0x1);
			}
		});
		return (!MemoryUtil.memGetBoolean(nopeAddr));
	}

	// public abstract String name();

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("active", this.Activated);
		if (this.blockPos != null)
			tag.put("pos", NbtUtils.writeBlockPos(this.blockPos));
		return tag;
	}
}
