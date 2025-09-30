/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tileentities;

import com.code.tama.tts.server.misc.NBTUtils;
import com.code.tama.tts.server.registries.TTSTileEntities;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompressedMultiblockTile extends BlockEntity {
    /** When adding to this always assume the "Multiblock Compression core" is 0 0 0 **/
    public Map<BlockPos, BlockState> stateMap = new HashMap<>();

    public CompressedMultiblockTile(BlockPos pos, BlockState state, Map<BlockPos, BlockState> map) {
        this(pos, state);
        this.stateMap = map;
    }

    public CompressedMultiblockTile(BlockPos pos, BlockState state) {
        super(TTSTileEntities.COMPRESSED_MULTIBLOCK_TILE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        AtomicInteger i = new AtomicInteger();
        stateMap.forEach((pos, state) -> {
            NBTUtils.WriteBlockPos("pos_" + i.get(), pos, nbt);
            nbt.put("state_" + i.get(), NbtUtils.writeBlockState(state));
            i.getAndIncrement();
        });
        nbt.putInt("size", i.get());
        super.saveAdditional(nbt);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void load(CompoundTag nbt) {
        if (nbt == null) return;
        int size = nbt.getInt("size");

        for (int i = 0; i < size; i++) {
            BlockState state =
                    NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("state_" + i));
            BlockPos pos = NBTUtils.ReadBlockPos("pos_" + i, nbt);
            stateMap.put(pos, state);
        }

        super.load(nbt);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
        super.handleUpdateTag(tag);
    }
}
