/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.tileentities.multiblock;

import com.code.tama.tts.server.misc.NBTUtils;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AverageMultiblockSlaveTile extends BlockEntity {
	@Setter
	@Getter
	public BlockPos master;

	public AverageMultiblockSlaveTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	@Override
	protected void saveAdditional(CompoundTag p_187471_) {
		NBTUtils.WriteBlockPos("master", this.master, p_187471_);
		super.saveAdditional(p_187471_);
	}

	@Override
	public void load(CompoundTag p_155245_) {
		this.master = NBTUtils.ReadBlockPos("master", p_155245_);
		super.load(p_155245_);
	}

	@Override
	public void setRemoved() {
		assert this.level != null;
		if (this.master == null)
			return;
		if (this.level.getBlockEntity(master) instanceof ImAMultiblock master)
			master.onSlaveRemoved();
		super.setRemoved();
	}
}
