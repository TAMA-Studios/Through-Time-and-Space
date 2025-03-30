package com.code.tama.mtm.server.worlds.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static com.code.tama.mtm.MTMMod.MODID;

public class NoiseGenSettings {
    public static final ResourceKey<NoiseGeneratorSettings> VAROS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(MODID, "varos"));
}
