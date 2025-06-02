package com.code.tama.mtm.server.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTUtils {
    public void WriteBlockPos(String id, BlockPos pos, CompoundTag tag) {
        tag.putIntArray(id, new int[] {pos.getX(), pos.getY(), pos.getZ()});
    }

    public BlockPos ReadBlockPos(String id, CompoundTag tag) {
        if(!tag.contains(id)) return null;
        int arr[] = tag.getIntArray(id);
        return new BlockPos(arr[0], arr[1], arr[2]);
    }
}