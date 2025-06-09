/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.subsystems;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractSubsystemBlock extends Block implements INBTSerializable<CompoundTag> {
    boolean Activated;

    public AbstractSubsystemBlock(Properties p_49795_) {
        super(p_49795_);
    }

    /** When the subsystem is activated **/
    public abstract void OnActivate(Level level, BlockPos blockPos);

    /** Called when the subsystem is tied into the engine block via fragment links
     * @param blockPos The position of the subsystem block being integrated, used for verifying that it in indeed a valid multiblock structure
     **/
    public void OnIntegration(Level level, BlockPos blockPos) {
    }

    public boolean IsValid(Level level, BlockPos blockPos) {
        AtomicReference<Boolean> IsValid = new AtomicReference<>(true);
        for(Direction direction : Direction.values()) {
            if(direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) continue;
            this.BlockMap().forEach((pos, state) -> {
                if (!IsValid.get()) return;
                if (!level.getBlockState(pos.offset(blockPos)).getBlock().defaultBlockState().equals(state)) {
                    IsValid.set(false);
                    return;
                }
            });
        }
        return IsValid.get();
    }

    /** the Map uses a relative BlockPos, and the Default Blockstate that make up this subsystem **/
    public abstract Map<BlockPos, BlockState> BlockMap();

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("active", this.Activated);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.Activated = nbt.getBoolean("active");
    }
}