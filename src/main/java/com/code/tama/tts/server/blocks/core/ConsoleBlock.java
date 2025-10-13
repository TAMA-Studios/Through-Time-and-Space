/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.core;

import com.code.tama.triggerapi.BlockUtils;
import com.code.tama.tts.server.entities.controls.ModularControl;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ConsoleBlock<T extends AbstractConsoleTile> extends Block implements EntityBlock {

    private final RegistryObject<BlockEntityType<T>> console;

    public ConsoleBlock(BlockBehaviour.Properties properties, RegistryObject<BlockEntityType<T>> consoleTile) {
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

        if (!updatedPos.equals(blockPos.below())) return;
        BlockState wasState = wasBlock.defaultBlockState();
        float offs;

        if (level.getBlockState(updatedPos).getBlock() instanceof SnowLayerBlock)
            offs = (wasBlock instanceof SnowLayerBlock) ? 0 : -1;
        else if (wasBlock instanceof SnowLayerBlock) offs = 1;
        else offs = BlockUtils.getDifferenceInHeight(wasState, level.getBlockState(updatedPos));

        Vec3 from = blockPos.getCenter().subtract(1, 1, 1);
        Vec3 to = blockPos.getCenter().add(1, 1, 1);
        AABB box = new AABB(from, to);
        level.getEntitiesOfClass(ModularControl.class, box).forEach(ent -> {
            ent.setPos(ent.position().x, ent.position().y + offs, ent.position().z);
            ent.Position = new Vec3(ent.position().x, ent.position().y + offs, ent.position().z);
        });

        super.neighborChanged(state, level, blockPos, wasBlock, updatedPos, p_60514_);
    }
}
