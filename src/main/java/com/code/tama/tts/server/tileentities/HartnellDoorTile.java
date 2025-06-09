/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.blocks.HartnellDoor;
import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class HartnellDoorTile extends BlockEntity {
    private boolean formed = false;
    public boolean IsOpen = false;

    public HartnellDoorTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.HARTNELL_DOOR.get(), pos, state);
    }

    public void setFormed(boolean formed) {
        this.formed = formed;
        setChanged();
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

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        deserializeNBT(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        deserializeNBT(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = this.serializeNBT();

        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void SetIsOpen(boolean IsOpen) {
        this.IsOpen = IsOpen;
    }

    public boolean IsOpen() {
        return ((HartnellDoor) this.level.getBlockState(this.getBlockPos()).getBlock()).IsOpen();
    }
}

