/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import java.util.ArrayList;

import com.code.tama.tts.server.items.core.NozzleItem;
import lombok.Getter;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

@Getter
public class WorkbenchTile extends BlockEntity {
	public ArrayList<Item> StoredItems = new ArrayList<>();
	public NozzleItem nozzle;

	public WorkbenchTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
}
