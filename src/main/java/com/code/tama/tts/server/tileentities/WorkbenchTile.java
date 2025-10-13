/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.items.core.NozzleItem;
import com.code.tama.tts.server.registries.TTSTileEntities;
import java.util.ArrayList;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Getter
public class WorkbenchTile extends BlockEntity {
    public ArrayList<Item> StoredItems = new ArrayList<>();
    public NozzleItem nozzle;

    public WorkbenchTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.WORKBENCH_TILE.get(), pos, state);
    }
}
