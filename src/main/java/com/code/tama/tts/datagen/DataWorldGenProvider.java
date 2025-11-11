/* (C) TAMA Studios 2025 */
package com.code.tama.tts.datagen;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.code.tama.tts.server.worlds.TBiomeModifiers;
import com.code.tama.tts.server.worlds.TConfiguredFeatures;
import com.code.tama.tts.server.worlds.TPlacedFeatures;
import com.code.tama.tts.server.worlds.biomes.TBiomes;
import com.code.tama.tts.server.worlds.dimension.TDimensions;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class DataWorldGenProvider extends DatapackBuiltinEntriesProvider {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.DIMENSION_TYPE, TDimensions::bootstrapType)
			.add(Registries.CONFIGURED_FEATURE, TConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, TPlacedFeatures::bootstrap)
			.add(ForgeRegistries.Keys.BIOME_MODIFIERS, TBiomeModifiers::bootstrap)
			.add(Registries.BIOME, TBiomes::boostrap).add(Registries.LEVEL_STEM, TDimensions::bootstrapStem);

	public DataWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries, BUILDER, Set.of(MODID));
	}
}
