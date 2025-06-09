/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class NBTUtils {
    public BlockPos ReadBlockPos(String id, CompoundTag tag) {
        if (!tag.contains(id)) return null;
        int arr[] = tag.getIntArray(id);
        return new BlockPos(arr[0], arr[1], arr[2]);
    }

    public void WriteBlockPos(String id, BlockPos pos, CompoundTag tag) {
        tag.putIntArray(id, new int[] {pos.getX(), pos.getY(), pos.getZ()});
    }
}
