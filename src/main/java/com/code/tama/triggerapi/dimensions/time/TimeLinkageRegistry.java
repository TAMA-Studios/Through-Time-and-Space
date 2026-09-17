/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * Tracks which levels participate in a {@link TimeLinkage}, so the block-change
 * hook can answer "is this level time-linked, and in what role" without parsing
 * resource-location strings on every single block update.
 * <p>
 * Stored on the overworld's {@code DimensionDataStorage} so linkages survive a
 * restart -- otherwise a player who travelled to the future yesterday would
 * find the propagation silently dead today.
 */
public final class TimeLinkageRegistry extends SavedData {

	private static final String DATA_NAME = "tts_time_linkages";

	/**
	 * Fast lookup: any member level (base, present or future) -> the linkage it
	 * belongs to.
	 */
	private final Map<ResourceKey<Level>, TimeLinkage> byMember = new HashMap<>();

	public static TimeLinkageRegistry get(MinecraftServer server) {
		// noinspection DataFlowIssue - overworld is always present on a running server
		return server.overworld().getDataStorage().computeIfAbsent(TimeLinkageRegistry::load, TimeLinkageRegistry::new,
				DATA_NAME);
	}

	public TimeLinkageRegistry() {
	}

	/* ======================== Lookup ======================== */

	/**
	 * The hot path. Called from the block-change hook for every placement/break in
	 * every dimension, so this must stay a plain hash lookup -- no allocation, no
	 * string work.
	 *
	 * @return the linkage this level belongs to, or {@code null} if it is not
	 *         time-linked.
	 */
	@Nullable public TimeLinkage lookup(ResourceKey<Level> level) {
		return byMember.get(level);
	}

	public boolean isLinked(ResourceKey<Level> level) {
		return byMember.containsKey(level);
	}

	/**
	 * @return true if the level may emit changes (i.e. it is the base of its
	 *         chain). Downstream levels return false -- this is the guard that
	 *         enforces one-directional causality.
	 */
	public boolean isSource(ResourceKey<Level> level) {
		TimeLinkage linkage = byMember.get(level);
		return linkage != null && linkage.isSource(level);
	}

	/* ======================== Mutation ======================== */

	public void register(TimeLinkage linkage) {
		byMember.put(linkage.base(), linkage);
		byMember.put(linkage.present(), linkage);
		byMember.put(linkage.future(), linkage);
		setDirty();
	}

	public void unregister(TimeLinkage linkage) {
		byMember.remove(linkage.base());
		byMember.remove(linkage.present());
		byMember.remove(linkage.future());
		setDirty();
	}

	/* ======================== Persistence ======================== */

	public static TimeLinkageRegistry load(CompoundTag tag) {
		TimeLinkageRegistry registry = new TimeLinkageRegistry();
		ListTag list = tag.getList("linkages", Tag.TAG_COMPOUND);
		for (int i = 0; i < list.size(); i++) {
			CompoundTag entry = list.getCompound(i);
			registry.register(new TimeLinkage(key(entry.getString("base")), key(entry.getString("present")),
					key(entry.getString("future"))));
		}
		return registry;
	}

	@Override
	public CompoundTag save(CompoundTag tag) {
		ListTag list = new ListTag();
		// byMember holds three references to each linkage; dedupe on the base key.
		byMember.values().stream().distinct().forEach(linkage -> {
			CompoundTag entry = new CompoundTag();
			entry.putString("base", linkage.base().location().toString());
			entry.putString("present", linkage.present().location().toString());
			entry.putString("future", linkage.future().location().toString());
			list.add(entry);
		});
		tag.put("linkages", list);
		return tag;
	}

	private static ResourceKey<Level> key(String id) {
		return ResourceKey.create(Registries.DIMENSION, new ResourceLocation(id));
	}
}