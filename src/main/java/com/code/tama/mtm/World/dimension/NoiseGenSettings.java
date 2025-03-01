package com.code.tama.mtm.World.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static com.code.tama.mtm.mtm.MODID;

public class NoiseGenSettings {
    public static final ResourceKey<NoiseGeneratorSettings> VAROS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(MODID, "varos"));
}
