package com.code.tama.tts.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;

@Getter @Setter @AllArgsConstructor
public class BotiChunkContainer implements INBTSerializable<CompoundTag> {
    BlockState state; BlockPos pos; int light;

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
        if(nbt == null) return;
        this.state = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), nbt.getCompound("state"));
        this.pos = NbtUtils.readBlockPos(nbt.getCompound("pos"));
        this.light = nbt.getInt("light");
    }
}