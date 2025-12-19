/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.misc.progressable.IWeldable;
import net.royawesome.jlibnoise.MathHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EmptyArtificialShellTile extends BlockEntity implements IWeldable {
	public int PlasmicShellPlates, StructuralBeams, Weld;
	public boolean ShouldMakeExt = false;

	public EmptyArtificialShellTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	/**
	 * @return The max weld, PlasmicShellPlates * 40 - 40 weld per plate
	 */
	@Override
	public int getMaxWeld() {
		return this.PlasmicShellPlates * 40;
	}

	@Override
	public int getWeld() {
		return this.Weld;
	}

	@Override
	public void setWeld(int weld) {
		this.Weld = MathHelper.clamp(weld, 0, this.getMaxWeld());
	}
}
