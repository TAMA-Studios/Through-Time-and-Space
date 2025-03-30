package com.code.tama.mtm.server.dimensions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record TARDISBiomeModifier(HolderSet<PlacedFeature> features) implements BiomeModifier {

    //TODO: Add features? maybe for ARS hallways or smth not sure
    public static final Codec<TARDISBiomeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(TARDISBiomeModifier::features)
    ).apply(instance, TARDISBiomeModifier::new));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {}

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}