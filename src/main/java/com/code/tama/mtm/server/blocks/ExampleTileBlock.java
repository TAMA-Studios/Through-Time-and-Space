package com.code.tama.mtm.server.blocks;

import com.code.tama.mtm.server.MTMTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// The block Tile Entity class must implement EntityBlock
public class ExampleTileBlock extends Block implements EntityBlock {
    public ExampleTileBlock(Properties props) {
        super(props);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        // Set this to your tile entity RegistryObject
        return MTMTileEntities.EXAMPLE_TILE.get().create(blockPos, blockState);
    }
}
