package com.code.tama.mtm.Dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TARDISDimensionChunkGenerator extends ChunkGenerator {

    public static final Codec<TARDISDimensionChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryOps.retrieveRegistryLookup(Registries.BIOME).forGetter(gen -> gen.biomeReg)).apply(instance, TARDISDimensionChunkGenerator::new));

    public final HolderLookup.RegistryLookup<Biome> biomeReg;
    public final RandomSource random;


    public TARDISDimensionChunkGenerator(HolderLookup.RegistryLookup<Biome> biomeReg) {
        super(new FixedBiomeSource(biomeReg.getOrThrow(Biomes.TARDIS_BIOME)));
        this.biomeReg = biomeReg;
        this.random = new SingleThreadedRandomSource(0);
    }

    // Leave (most) these functions below empty to stop stuff from generating

    @Override
    public void createReferences(WorldGenLevel pLevel, StructureManager pStructureManager, ChunkAccess pChunk) {}

    @Override
    public @NotNull Codec<TARDISDimensionChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public void applyCarvers(WorldGenRegion p_223043_, long p_223044_, RandomState p_223045_, BiomeManager p_223046_, StructureManager p_223047_, ChunkAccess p_223048_, GenerationStep.Carving p_223049_) {}

    @Override
    public void buildSurface(WorldGenRegion p_223050_, StructureManager p_223051_, RandomState p_223052_, ChunkAccess p_223053_) {}

    @Override
    public void spawnOriginalMobs(WorldGenRegion p_62167_) {}


    @Override
    public int getGenDepth() {
        return 384;
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender p_223210_, RandomState p_223211_, StructureManager p_223212_, ChunkAccess access) {
        return CompletableFuture.completedFuture(access);
    }

    @Override
    public int getSeaLevel() {
        return -64;
    }

    @Override
    public int getMinY() {
        return -64;
    }

    @Override
    public int getBaseHeight(int p_223032_, int p_223033_, Heightmap.Types p_223034_, LevelHeightAccessor p_223035_, RandomState p_223036_) {
        return 0;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int p_223028_, int p_223029_, @NotNull LevelHeightAccessor level, @NotNull RandomState p_223031_) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public void addDebugScreenInfo(List<String> p_223175_, RandomState p_223176_, BlockPos p_223177_) {}
}

