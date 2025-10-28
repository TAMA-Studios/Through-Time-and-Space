/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.entities;

import com.code.tama.tts.server.tileentities.ExteriorTile;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@Getter
public class FallingExteriorEntity extends FallingBlockEntity {
	private CompoundTag tileData;

	public FallingExteriorEntity(EntityType<? extends FallingBlockEntity> type, Level level) {
		super(type, level);
	}

	public FallingExteriorEntity(Level level, double x, double y, double z, BlockState state, ExteriorTile tile) {
		super(EntityType.FALLING_BLOCK, level);
		this.setPos(x, y, z);
		if (tile != null)
			this.tileData = tile.saveWithoutMetadata();
		else
			this.tileData = new CompoundTag();
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (tileData != null)
			tag.put("TileData", tileData);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("TileData"))
			tileData = tag.getCompound("TileData");
	}

	// protected void placeBlock(Level level, BlockPos pos) {
	// super.placeBlock(level, pos);
	// if (tileData != null && level.getBlockEntity(pos) instanceof ExteriorTile
	// tile) {
	// tile.load(tileData); // restore data
	// tile.setChanged();
	// }
	// }
}
