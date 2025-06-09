/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.biomes;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class GallifreyRegion extends Region {
    public GallifreyRegion(ResourceLocation name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addBiome(mapper, ParameterPoints.GallifreyPlains(), MBiomes.GALLIFREYAN_PLAINS);
        this.addBiome(mapper, ParameterPoints.GallifreyDesert(), MBiomes.GALLIFREYAN_DESERT);
    }
}