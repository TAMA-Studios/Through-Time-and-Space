/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.compat.cct.tiles;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;

public class TardisCCInterfaceTile extends BlockEntity {
	public TardisCCInterfaceTile(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	public LazyOptional<ITARDISLevel> getCap() {
		assert this.level != null;
		return TARDISLevelCapability.GetTARDISCapSupplier(this.level);
	}
}
