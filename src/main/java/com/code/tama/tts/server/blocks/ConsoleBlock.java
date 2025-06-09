/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import com.code.tama.tts.server.tileentities.ConsoleTile;
import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

public class ConsoleBlock extends Block implements EntityBlock {

    private final RegistryObject<BlockEntityType<ConsoleTile>> console;
    public ConsoleBlock(BlockBehaviour.Properties properties, RegistryObject<BlockEntityType<ConsoleTile>> consoleTile) {
        super(properties);
        this.console = consoleTile;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.console.get().create(pos, state);
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == console.get() ? AbstractConsoleTile::tick : null;
    }
}
