package com.code.tama.mtm.TileEntities;

import com.code.tama.mtm.Blocks.HartnellDoor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HartnellDoorTile extends BlockEntity {
    private boolean formed = false;
    public boolean IsOpen = false;

    public HartnellDoorTile(BlockPos pos, BlockState state) {
        super(MTileEntities.HARTNELL_DOOR.get(), pos, state);
    }

    public void setFormed(boolean formed) {
        this.formed = formed;
        setChanged();
    }

    public boolean isFormed() {
        return this.formed;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Formed", this.formed);
        tag.putBoolean("IsOpen", this.IsOpen);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.formed = tag.getBoolean("Formed");
        this.IsOpen = tag.getBoolean("IsOpen");
        ((HartnellDoor) this.getBlockState().getBlock()).SetIsOpen(this.IsOpen);
    }

    public void SetIsOpen(boolean IsOpen) {
        this.IsOpen = IsOpen;
    }

    public boolean IsOpen() {
        return this.IsOpen;
    }
}

