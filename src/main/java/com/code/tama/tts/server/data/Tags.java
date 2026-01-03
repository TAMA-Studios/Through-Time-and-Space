/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class Tags {
	public static final ResourceKey<Registry<Biome>> TTS_BIOMES = createRegistryKey("tts/biome");

	private static <T> ResourceKey<Registry<T>> createRegistryKey(String key) {
		return ResourceKey.createRegistryKey(new ResourceLocation(key));
	}

	public static class Items {
		public static final TagKey<Item> SONICABLE = TagKey.create(Registries.ITEM, UniversalCommon.modRL("sonicable"));
	}

	public static class Blocks {
		public static final TagKey<Block> SONICABLE = TagKey.create(Registries.BLOCK,
				UniversalCommon.modRL("sonicable"));
	}

	public static class Biomes {
		public static final TagKey<Biome> IS_GALLIFREY = TagKey.create(Registries.BIOME,
				UniversalCommon.modRL("gallifrey"));
	}

	public static class Entities {
		public static final TagKey<EntityType<?>> SONICABLE = TagKey.create(Registries.ENTITY_TYPE,
				UniversalCommon.modRL("sonicable"));
	}

	public static class TTS {
		public static class Biome {
			public static final TagKey<net.minecraft.world.level.biome.Biome> CANT_TRAVEL_TO = TagKey.create(TTS_BIOMES,
					UniversalCommon.modRL("cant_travel"));
		}
	}
}
