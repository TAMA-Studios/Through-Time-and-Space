package com.code.tama.mtm.datagen;

import com.code.tama.mtm.server.worlds.MBiomeModifiers;
import com.code.tama.mtm.server.worlds.MConfiguredFeatures;
import com.code.tama.mtm.server.worlds.ModPlacedFeatures;
import com.code.tama.mtm.server.worlds.biomes.MBiomes;
import com.code.tama.mtm.server.worlds.dimension.MDimensions;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.code.tama.mtm.MTMMod.MODID;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, MDimensions::bootstrapType)
            .add(Registries.CONFIGURED_FEATURE, MConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, MBiomeModifiers::bootstrap)
            .add(Registries.BIOME, MBiomes::boostrap)
            .add(Registries.LEVEL_STEM, MDimensions::bootstrapStem);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(MODID));
    }
}