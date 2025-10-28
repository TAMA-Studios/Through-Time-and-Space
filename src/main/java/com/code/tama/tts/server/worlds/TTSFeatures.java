/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds;

import com.code.tama.tts.TTSMod;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TTSFeatures {

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES,
			TTSMod.MODID);

	public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> CRATER = FEATURES.register("crater",
			() -> new Crater(ProbabilityFeatureConfiguration.CODEC));
}
