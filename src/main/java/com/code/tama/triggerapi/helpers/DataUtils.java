/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public class DataUtils {
	public static String getEntityData(Entity entity, String key, String defaultValue) {
		CompoundTag tag = entity.getPersistentData();
		return tag.contains(key) ? tag.getString(key) : defaultValue;
	}

	public static CompoundTag saveEntityData(Entity entity, String key, String value) {
		CompoundTag tag = entity.getPersistentData();
		tag.putString(key, value);
		return tag;
	}

	public static <T extends INBTSerializable<CompoundTag>> CompoundTag saveToNBT(Level world, BlockPos pos, String key,
			T data) {
		CompoundTag tag = world.getBlockEntity(pos).getPersistentData();
		tag.put(key, data.serializeNBT());
		return tag;
	}
}
