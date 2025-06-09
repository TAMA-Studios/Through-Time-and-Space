/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.dimension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import static com.code.tama.tts.TTSMod.MODID;

public class NoiseGenSettings {
    public static final ResourceKey<NoiseGeneratorSettings> VAROS = ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(MODID, "varos"));
}
