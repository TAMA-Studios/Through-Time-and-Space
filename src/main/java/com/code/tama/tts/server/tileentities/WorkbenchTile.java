package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.registries.TTSTileEntities;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

@Getter
public class WorkbenchTile extends BlockEntity {
    public ArrayList<Item> StoredItems = new ArrayList<>();
    public WorkbenchTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.WORKBENCH_TILE.get(), pos, state);
    }
}