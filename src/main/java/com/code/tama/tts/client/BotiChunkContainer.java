/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

@Getter
@Setter
@AllArgsConstructor
public class BotiChunkContainer implements INBTSerializable<CompoundTag> {
    BlockState state;
    BlockPos pos;
    int light;

    public BotiChunkContainer(CompoundTag tag) {
        this.deserializeNBT(tag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("pos", NbtUtils.writeBlockPos(pos));
        nbt.put("state", NbtUtils.writeBlockState(state));
        nbt.putInt("light", light);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt == null) return;
        this.state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("state"));
        this.pos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
        this.light = nbt.getInt("light");
    }

    public void encode(FriendlyByteBuf buf) {
        // Write BlockPos compactly (3 ints)
        buf.writeBlockPos(pos);

        // Write BlockState as raw ID (includes properties!)
        int stateId = Block.BLOCK_STATE_REGISTRY.getId(state);
        buf.writeVarInt(stateId);

        // Write light as VarInt
        buf.writeVarInt(light);
    }

    public static BotiChunkContainer decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();

        // Read BlockState
        BlockState state = Block.BLOCK_STATE_REGISTRY.byId(buf.readVarInt());

        int light = buf.readVarInt();

        return new BotiChunkContainer(state, pos, light);
    }

    public static void encodeList(List<BotiChunkContainer> list, FriendlyByteBuf buf) {
        buf.writeVarInt(list.size());
        for (BotiChunkContainer container : list) {
            container.encode(buf);
        }
    }

    public static List<BotiChunkContainer> decodeList(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<BotiChunkContainer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(BotiChunkContainer.decode(buf));
        }
        return list;
    }
}
