/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import com.code.tama.tts.server.entities.controls.ModularControl;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import com.code.tama.tts.server.tileentities.ConsoleTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ConsoleBlock extends Block implements EntityBlock {

    private final RegistryObject<BlockEntityType<ConsoleTile>> console;

    public ConsoleBlock(
            BlockBehaviour.Properties properties, RegistryObject<BlockEntityType<ConsoleTile>> consoleTile) {
        super(properties);
        this.console = consoleTile;
    }

    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            @NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == console.get() ? AbstractConsoleTile::tick : null;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return this.console.get().create(pos, state);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(
            @NotNull BlockState state,
            @NotNull Level level,
            @NotNull BlockPos blockPos,
            @NotNull Block wasBlock,
            @NotNull BlockPos updatedPos,
            boolean p_60514_) {
        boolean WasFullNowSlab = !(wasBlock instanceof SlabBlock)
                && (level.getBlockState(updatedPos).getBlock() instanceof SlabBlock);
        boolean WasSlabNowFull = (wasBlock instanceof SlabBlock)
                && (!(level.getBlockState(updatedPos).getBlock() instanceof SlabBlock)
                        && level.getBlockState(updatedPos).getBlock() instanceof Block);
        float ModifyValue = WasFullNowSlab ? -0.5f : (WasSlabNowFull ? 0.5f : 0f);
        if (ModifyValue != 0f) {
            Vec3 from = blockPos.offset(-1, -1, -1).getCenter();
            Vec3 to = blockPos.offset(1, 1, 1).getCenter();
            System.out.printf("%s, %s%n", to, from);
            AABB box = new AABB(from, to);
            level.getEntitiesOfClass(ModularControl.class, box)
                    .forEach(ent -> ent.setPos(ent.position().x, ent.position().y + ModifyValue, ent.position().z));
        }
        super.neighborChanged(state, level, blockPos, wasBlock, updatedPos, p_60514_);
    }
}
