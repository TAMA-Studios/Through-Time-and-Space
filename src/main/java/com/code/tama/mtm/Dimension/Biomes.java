package com.code.tama.mtm.Dimension;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.code.tama.mtm.mtm.MODID;

public class Biomes {
    public static final DeferredRegister<Codec<? extends ChunkGenerator>> CHUNK_GENERATORS = DeferredRegister.create(Registries.CHUNK_GENERATOR, MODID);
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);

    public static final ResourceKey<Biome> TARDIS_BIOME = ResourceKey.create(Registries.BIOME, new ResourceLocation("mtm", "tardis"));

    public static final RegistryObject<Codec<? extends ChunkGenerator>> TARDIS_CHUNK_GENERATOR = CHUNK_GENERATORS.register(MODID, () -> TARDISDimensionChunkGenerator.CODEC);

    public static final RegistryObject<Codec<TARDISBiomeModifier>> DEFAULT_BIOME_MOD = BIOME_MODIFIERS.register("tardis_chunk_generator", () -> TARDISBiomeModifier.CODEC);

}
