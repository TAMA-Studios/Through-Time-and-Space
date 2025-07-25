/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class AbstractSubsystem implements INBTSerializable<CompoundTag> {
    boolean Activated;

    @Getter
    @Setter
    BlockPos blockPos;

    public boolean isActivated(Level level) {
        return this.Activated && this.IsValid(level, blockPos);
    }

    /**
     * the Map uses a relative BlockPos, and the Default Blockstate that make up
     * this subsystem
     **/
    public abstract Map<BlockPos, BlockState> BlockMap();

    public boolean IsValid(Level level, BlockPos blockPos) {
        AtomicReference<Boolean> IsValid = new AtomicReference<>(true);
        for (Direction direction : Direction.values()) {
            if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) continue;
            this.BlockMap().forEach((pos, state) -> {
                if (!IsValid.get()) return;
                if (!level.getBlockState(pos.offset(blockPos))
                        .getBlock()
                        .defaultBlockState()
                        .equals(state)) {
                    IsValid.set(false);
                    return;
                }
            });
        }
        return IsValid.get();
    }

    /** When the subsystem is activated **/
    public abstract void OnActivate(Level level, BlockPos blockPos);

    /** When the subsystem is de-activated **/
    public abstract void OnDeActivate(Level level, BlockPos blockPos);

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.Activated = nbt.getBoolean("active");
        this.blockPos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("active", this.Activated);
        if (this.blockPos != null) tag.put("pos", NbtUtils.writeBlockPos(this.blockPos));
        return tag;
    }
}
