/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class NBTUtils {
	public static BlockPos ReadBlockPos(String id, CompoundTag tag) {
		if (!tag.contains(id))
			return null;
		int arr[] = tag.getIntArray(id);
		return new BlockPos(arr[0], arr[1], arr[2]);
	}

	public static void WriteBlockPos(String id, BlockPos pos, CompoundTag tag) {
		tag.putIntArray(id, new int[]{pos.getX(), pos.getY(), pos.getZ()});
	}

	/**
	 * Get a Map from a CompoundTag.
	 *
	 * @param id
	 *            The key in the parent tag.
	 * @param tag
	 *            The parent CompoundTag.
	 * @return A Map<String, String> reconstructed from NBT.
	 */
	public static List<String> getList(String id, CompoundTag tag) {
		List<String> list = new java.util.ArrayList<>();

		if (tag.contains(id, Tag.TAG_LIST)) {
			ListTag listTag = tag.getList(id, Tag.TAG_COMPOUND);

			for (int i = 0; i < listTag.size(); i++) {
				CompoundTag entryTag = listTag.getCompound(i);
				String value = entryTag.getString("value");
				list.add(value);
			}
		}

		return list;
	}

	/**
	 * Get a Map from a CompoundTag.
	 *
	 * @param id
	 *            The key in the parent tag.
	 * @param tag
	 *            The parent CompoundTag.
	 * @return A Map<String, String> reconstructed from NBT.
	 */
	public static Map<String, String> getMap(String id, CompoundTag tag) {
		Map<String, String> map = new HashMap<>();

		if (tag.contains(id, Tag.TAG_LIST)) {
			ListTag listTag = tag.getList(id, Tag.TAG_COMPOUND);

			for (int i = 0; i < listTag.size(); i++) {
				CompoundTag entryTag = listTag.getCompound(i);
				String key = entryTag.getString("key");
				String value = entryTag.getString("value");
				map.put(key, value);
			}
		}

		return map;
	}

	public static Map<UUID, PlayerPosition> getPlayerPosMap(String id, CompoundTag tag) {
		Map<UUID, PlayerPosition> map = new HashMap<>();

		if (tag.contains(id, Tag.TAG_LIST)) {
			ListTag listTag = tag.getList(id, Tag.TAG_COMPOUND);

			for (int i = 0; i < listTag.size(); i++) {
				CompoundTag entryTag = listTag.getCompound(i);
				UUID key = entryTag.getUUID("key");
				String value = entryTag.getString("value");
				map.put(key, PlayerPosition.fromString(value));
			}
		}

		return map;
	}

	public static void putList(String id, List<String> list, CompoundTag tag) {
		ListTag listTag = new ListTag();

		for (String entry : list) {
			CompoundTag entryTag = new CompoundTag();
			entryTag.putString("value", String.valueOf(entry));
			listTag.add(entryTag);
		}

		tag.put(id, listTag);
	}

	/**
	 * Put a Map into a CompoundTag.
	 *
	 * @param id
	 *            The key in the parent tag.
	 * @param map
	 *            The map to store. Keys and values are serialized as strings.
	 * @param tag
	 *            The parent CompoundTag.
	 */
	public static void putMap(String id, Map<String, String> map, CompoundTag tag) {
		ListTag listTag = new ListTag();

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			CompoundTag entryTag = new CompoundTag();
			entryTag.putString("key", String.valueOf(entry.getKey()));
			entryTag.putString("value", String.valueOf(entry.getValue()));
			listTag.add(entryTag);
		}

		tag.put(id, listTag);
	}

	public static void putPlayerPosMap(String id, Map<UUID, PlayerPosition> map, CompoundTag tag) {
		ListTag listTag = new ListTag();

		for (Map.Entry<UUID, PlayerPosition> entry : map.entrySet()) {
			CompoundTag entryTag = new CompoundTag();
			entryTag.putUUID("key", entry.getKey());
			entryTag.putString("value", entry.getValue().toString());
			listTag.add(entryTag);
		}

		tag.put(id, listTag);
	}
}
