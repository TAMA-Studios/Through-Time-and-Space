/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.blocks.HartnellDoorMultiBlock;
import com.code.tama.tts.server.registries.TTSTileEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HartnellDoorTilePlaceholder extends BlockEntity {
    public BlockPos Master = BlockPos.ZERO;
    private boolean IsOpen = false;

    public HartnellDoorTilePlaceholder(BlockPos pos, BlockState state) {
        super(TTSTileEntities.HARTNELL_DOOR_PLACEHOLDER.get(), pos, state);
    }

    public void SetIsOpen(boolean IsOpen) {
        this.IsOpen = IsOpen;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        this.IsOpen = tag.getBoolean("IsOpen");
        this.Master = BlockPos.of(tag.getLong("Master"));
        ((HartnellDoorMultiBlock) this.getBlockState().getBlock()).IsOpen = this.IsOpen;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        deserializeNBT(tag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        deserializeNBT(tag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("IsOpen", this.IsOpen);
        tag.putLong("Master", this.Master.asLong());
        return tag;
    }
}
